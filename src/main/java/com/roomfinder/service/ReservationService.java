package com.roomfinder.service;

import java.time.LocalDateTime;
import java.util.List;

import com.roomfinder.vo.ReservationVO;

public interface ReservationService {

	//C
	public void insertReservation(ReservationVO vo);
	//R
	public List<ReservationVO> getMyReservation(String user_email);
	public int getInsertableCheck(ReservationVO vo);
	public List<ReservationVO> getRoomReservationList(int room_seq);
	public List<ReservationVO> getRoomDateReservationList(ReservationVO vo);
	public List<ReservationVO> getRoomAfterNowReservationList(int room_seq);
	public int getAfterNowReservationCount(int room_seq);
	//U
		
	//D
}
