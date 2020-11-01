package com.roomfinder.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.config.LoginManagement;
import com.roomfinder.mapper.AccountMapper;
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
	
	/** 사용자 추가 API */
	@PostMapping("/user")
	public void postUser(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO vo) {
		System.out.println("postUser 요청");
		System.out.println(vo);
		vo.setPassword(BCrypt.hashpw(vo.getPassword(), BCrypt.gensalt()));
		System.out.println("비크립트 해시 : " + vo.getPassword());
		try {
			accountService.insertAccount(vo);
			try {
				accountService.insertUser(vo);
				response.setStatus(HttpStatus.CREATED.value());
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("계정에선 에러 안났는데 유저에서 에라남 그래서 account삭제해줌");
				accountService.deleteAccount(vo.getEmail());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.CONFLICT.value());
		}
	}
	
	/** 매장 추가 API */
	@PostMapping("/store")
	public void postStore(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreVO vo) {
		System.out.println("postStore 요청");
		System.out.println(vo);
		vo.setPassword(BCrypt.hashpw(vo.getPassword(), BCrypt.gensalt()));
		System.out.println("비크립트 해시 : " + vo.getPassword());
		try {
			accountService.insertAccount(vo);
			try {
				accountService.insertStore(vo);
				response.setStatus(HttpStatus.CREATED.value());
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("계정에선 에러 안났는데 스토어에서 에라남 그래서 account삭제해줌");
				accountService.deleteAccount(vo.getEmail());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.CONFLICT.value());
		}
	}
	
	/** 로그인 토큰 발급 API */
	@PostMapping("/auth")
	public AccountVO auth(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountVO vo) {
		System.out.println("auth 요청");
		System.out.println(vo);
		AccountVO loginAccount = new AccountVO();
		AccountVO account = accountService.getAccount(vo.getEmail());
		if(account != null) {
			System.out.println("계정 있음");
			
			if(BCrypt.checkpw(vo.getPassword(), account.getPassword())) {
				System.out.println("비밀번호 맞음");
				if(account.getAccount_type().equals("user")) {
					UserVO user = accountService.getUser(vo.getEmail());
					loginAccount.setEmail(user.getEmail());
					loginAccount.setAccount_type("user");
					String jwt = LoginManagement.createJWT(account);
					LoginManagement.issueJWT(jwt, response);
					response.setStatus(HttpStatus.OK.value());
				} else if(account.getAccount_type().equals("store")) {
					StoreVO store = accountService.getStore(vo.getEmail());
					loginAccount.setEmail(store.getEmail());
					loginAccount.setAccount_type("store");
					String jwt = LoginManagement.createJWT(account);
					LoginManagement.issueJWT(jwt, response);
					response.setStatus(HttpStatus.OK.value());
				}
			} else {
				System.out.println("비밀번호 오류");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
			
		} else {
			System.out.println("해당 이메일로 가입된 계정 없음");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		
		return loginAccount;
	}
	
	/** 특정 계정 조회 API */
	@GetMapping("/{email}")
	public AccountVO getAccount(HttpServletRequest request, HttpServletResponse response, @PathVariable("email") String email) {
		System.out.println("getAccount 요청");
		AccountVO account = null;
		account = accountService.getAccount(email);
		if(account.getAccount_type().equals("user")) {
			account = accountService.getUser(email);
			account.setAccount_type("user");
		} else if(account.getAccount_type().equals("store")) {
			account = accountService.getStore(email);
			account.setAccount_type("store");
		}
		
		return account;
	}
	
	/** 계정 타입 별 리스트 조회 */
	@GetMapping("/list/{account_type}")
	public List<AccountVO> getAccountTypeList(HttpServletRequest request, HttpServletResponse response, @PathVariable("account_type") String account_type) {
		System.out.println("getAccountTypeList 요청");
		List<AccountVO> resultList = null;
		if(account_type.equals("user")) {
			ArrayList<AccountVO> tmpList = new ArrayList<>();
			List<UserVO> userList = accountService.getUserList();
			for(UserVO user : userList) {
				user.setAccount_type("user");
				tmpList.add(user);
			}
			resultList = tmpList;
		} else if(account_type.equals("store")) {
			ArrayList<AccountVO> tmpList = new ArrayList<>();
			List<StoreVO> storeList = accountService.getStoreList();
			for(StoreVO store : storeList) {
				store.setAccount_type("store");
				tmpList.add(store);
			}
			resultList = tmpList;
		}
		return resultList;
	}
	
	/** 비밀번호 변경 API */
	@PatchMapping("/password")
	public void patchPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountVO vo) {
		System.out.println("patchPassword 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getEmail())) { // 로그인 된 본인이면
			vo.setPassword(BCrypt.hashpw(vo.getPassword(), BCrypt.gensalt()));
			accountService.updatePassword(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 비번 바꾸기 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 사용자 핸드폰 번호 변경 API */
	@PatchMapping("/phone")
	public void patchPhone(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO vo) {
		System.out.println("patchPhone 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getEmail())) { // 로그인 된 본인이면
			accountService.updatePhone(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 폰번호 바꾸기 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 사용자 이름 변경 API */
	@PatchMapping("/username")
	public void patchUserName(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO vo) {
		System.out.println("patchUserName 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getEmail())) { // 로그인 된 본인이면
			accountService.updateUserName(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 유저이름 바꾸기 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
}
