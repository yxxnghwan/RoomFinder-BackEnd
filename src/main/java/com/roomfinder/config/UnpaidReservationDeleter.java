package com.roomfinder.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.roomfinder.mapper.ReservationMapper;
import com.roomfinder.service.ReservationService;

@Configuration
@DependsOn(value = {"reservationService"})
public class UnpaidReservationDeleter {
	
	@Autowired
	ReservationService reservationService;
	
	@PostConstruct
	public void startServer() {
		System.out.println("서버 켜질 때 버그난 예약정보들 삭제할겁니다.");
		reservationService.deleteAllUnpaidReservation();
	}
}
