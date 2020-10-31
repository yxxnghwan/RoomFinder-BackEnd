package com.roomfinder.service;

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
}
