package com.roomfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	private boolean isInsertableReservation(ReservationVO vo) {
		int insertableInt = reservationService.getInsertableCheck(vo);
		if(insertableInt > 0) {
			return false;
		}
		if(vo.getStart_time().isAfter(vo.getEnd_time())) {
			System.out.println("스타트타임이 어케 엔드보다 높냐");
			return false;
		}
		return true;
	}
	
	@PostMapping
	public void insertReservation(HttpServletRequest request, HttpServletResponse response, @RequestBody ReservationVO vo) {
		System.out.println("insertReservation 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getUser_email())) { // 로그인 된 본인이면
			if(isInsertableReservation(vo)) {
				reservationService.insertReservation(vo);
				response.setStatus(HttpStatus.CREATED.value());
			} else {
				response.setStatus(HttpStatus.CONFLICT.value());
			}
		} else {
			System.out.println("본인만 예약 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	@GetMapping("list")
	public List<ReservationVO> getMyReservationList(HttpServletRequest request, HttpServletResponse response, @RequestBody ReservationVO vo) {
		System.out.println("getMyReservationList 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getUser_email())) { // 로그인 된 본인이면	
			response.setStatus(HttpStatus.CREATED.value());
			return reservationService.getMyReservation(vo.getUser_email());
		} else {
			System.out.println("본인만 예약조회 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		return null;
	}
	
	@GetMapping("room/{room_seq}")
	public List<ReservationVO> getRoomReservationList(HttpServletRequest request, HttpServletResponse response, @PathVariable("room_seq") int room_seq) {
		System.out.println("getRoomReservationList 요청");
		return reservationService.getRoomReservationList(room_seq);
	}
	
	@GetMapping("room/{room_seq}/{str_date}")
	public List<ReservationVO> getRoomDateReservationList(HttpServletRequest request, HttpServletResponse response, @PathVariable("room_seq") int room_seq, @PathVariable("str_date") String str_date) {
		System.out.println("getRoomDateReservationList요청");
		ReservationVO vo = new ReservationVO();
		vo.setRoom_seq(room_seq);
		vo.setStr_date(str_date);
		return reservationService.getRoomDateReservationList(vo);
	}
	
}
