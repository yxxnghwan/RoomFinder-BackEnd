package com.roomfinder.service;

import java.util.List;

import com.roomfinder.vo.ReservationVO;

public interface ReservationService {

	//C
	public void insertReservation(ReservationVO vo);
	//R
	public List<ReservationVO> getMyReservation(String user_email);
	//U
		
	//D
}
