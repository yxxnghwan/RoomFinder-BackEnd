package com.roomfinder.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.roomfinder.vo.ReservationVO;

@Mapper
public interface ReservationMapper {
	
	//C
	public void insertReservation(ReservationVO vo);
	//R
	public List<ReservationVO> getMyReservation(String user_email);
	public int getInsertableCheck(ReservationVO vo);
	public List<ReservationVO> getRoomReservationList(int room_seq);
	public List<ReservationVO> getRoomDateReservationList(ReservationVO vo);
	public List<ReservationVO> getRoomAfterNowReservationList(int room_seq);
	public int getAfterNowReservationCount(int room_seq);
	public int getReservationSeq(ReservationVO vo);
	public ReservationVO getReservation(int reservation_seq);
	//U
	
	//D
	public void deleteReservation(int reservation_seq);
	public void deleteUnpaidReservation(int reservation_seq);
	public void deleteAllUnpaidReservation();
}
