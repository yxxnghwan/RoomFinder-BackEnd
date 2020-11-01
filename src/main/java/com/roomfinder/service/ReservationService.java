package com.roomfinder.service;

import java.time.LocalDateTime;
import java.util.List;

import com.roomfinder.vo.ReservationVO;

public interface ReservationService {

	//C
	public void insertReservation(ReservationVO vo);
	//R
	public List<ReservationVO> getMyReservation(String user_email);
	public int getStartTimeCheck(LocalDateTime time);
	public int getEndTimeCheck(LocalDateTime time);
	//U
		
	//D
}
