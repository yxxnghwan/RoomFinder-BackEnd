package com.roomfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.service.StoreService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.StoreImageVO;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/store")
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	@PostMapping("/image")
	public void postImage(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreImageVO vo) {
		System.out.println("postImage 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면
			storeService.insertStoreImage(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 이미지 추가 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}
