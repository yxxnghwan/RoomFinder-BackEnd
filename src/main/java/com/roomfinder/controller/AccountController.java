package com.roomfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.config.LoginManagement;
import com.roomfinder.service.AccountService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.StoreVO;
import com.roomfinder.vo.UserVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/user")
	public void postUser(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO vo) {
		System.out.println("postUser 요청");
		System.out.println(vo);
		vo.setPassword(BCrypt.hashpw(vo.getPassword(), BCrypt.gensalt()));
		System.out.println("비크립트 해시 : " + vo.getPassword());
		try {
			accountService.insertAccount(vo);
			accountService.insertUser(vo);
			response.setStatus(HttpStatus.CREATED.value());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.CONFLICT.value());
		}
	}
	
	@PostMapping("/store")
	public void postStore(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreVO vo) {
		System.out.println("postStore 요청");
		System.out.println(vo);
		vo.setPassword(BCrypt.hashpw(vo.getPassword(), BCrypt.gensalt()));
		System.out.println("비크립트 해시 : " + vo.getPassword());
		try {
			accountService.insertAccount(vo);
			accountService.insertStore(vo);
			response.setStatus(HttpStatus.CREATED.value());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.CONFLICT.value());
		}
	}
	
	@PostMapping("auth")
	public AccountVO auth(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountVO vo) {
		System.out.println("auth 요청");
		System.out.println(vo);
		AccountVO loginAccount = new AccountVO();
		AccountVO account = accountService.getAccount(vo);
		if(account != null) {
			System.out.println("계정 있음");
			
			if(BCrypt.checkpw(vo.getPassword(), account.getPassword())) {
				System.out.println("비밀번호 맞음");
				if(account.getAccount_type().equals("user")) {
					UserVO user = accountService.getUser(vo);
					loginAccount.setEmail(user.getEmail());
					loginAccount.setAccount_type("user");
					String jwt = LoginManagement.createJWT(account);
					LoginManagement.issueJWT(jwt, response);
				} else if(account.getAccount_type().equals("store")) {
					StoreVO store = accountService.getStore(vo);
					loginAccount.setEmail(store.getEmail());
					loginAccount.setAccount_type("store");
					String jwt = LoginManagement.createJWT(account);
					LoginManagement.issueJWT(jwt, response);
				}
			} else {
				System.out.println("비밀번호 오류");
			}
			
		} else {
			System.out.println("해당 이메일로 가입된 계정 없음");
		}
		
		return loginAccount;
	}
	
	
	@GetMapping("/{email}")
	public AccountVO getAccount(HttpServletRequest request, HttpServletResponse response, @PathVariable("email") String email) {
		AccountVO account = new AccountVO();
		account.setEmail(email);
		account = accountService.getAccount(account);
		if(account.getAccount_type().equals("user")) {
			account = accountService.getUser(account);
			account.setAccount_type("user");
		} else if(account.getAccount_type().equals("store")) {
			account = accountService.getStore(account);
			account.setAccount_type("store");
		}
		
		return account;
	}
	
}
