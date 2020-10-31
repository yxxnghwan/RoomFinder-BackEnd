package com.roomfinder.controller;

import java.util.List;

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

import com.roomfinder.service.ReservationService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.ReservationVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@PostMapping
	public void insertReservation(HttpServletRequest request, HttpServletResponse response, @RequestBody ReservationVO vo) {
		System.out.println("insertReservation 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getUser_email())) { // 로그인 된 본인이면
			reservationService.insertReservation(vo);
			response.setStatus(HttpStatus.CREATED.value());
		} else {
			System.out.println("본인만 예약 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	@GetMapping("/{user_email}")
	public List<ReservationVO> getMyReservation(HttpServletRequest request, HttpServletResponse response, @PathVariable("user_email") String user_email) {
		System.out.println("getMyReservation 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(user_email)) { // 로그인 된 본인이면
			//reservationService.getMyReservation(user_email);
			response.setStatus(HttpStatus.CREATED.value());
		} else {
			System.out.println("본인만 예약조회 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		return null;
	}
}
