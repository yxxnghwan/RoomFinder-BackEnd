package com.roomfinder.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomfinder.mapper.ReservationMapper;
import com.roomfinder.vo.ReservationVO;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	ReservationMapper reservationMapper;
	
	@Override
	public void insertReservation(ReservationVO vo) {
		// TODO Auto-generated method stub
		reservationMapper.insertReservation(vo);
	}
	
	@Override
	public List<ReservationVO> getMyReservation(String user_email) {
		// TODO Auto-generated method stub
		return reservationMapper.getMyReservation(user_email);
	}
	
	@Override
	public int getInsertableCheck(ReservationVO vo) {
		// TODO Auto-generated method stub
		return reservationMapper.getInsertableCheck(vo);
	}
}
