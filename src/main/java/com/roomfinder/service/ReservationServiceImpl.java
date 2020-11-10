package com.roomfinder.service;

import java.time.LocalDateTime;
import java.util.Iterator;
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
		List<ReservationVO> reservationList = reservationMapper.getMyReservation(user_email);
		Iterator<ReservationVO> itr = reservationList.iterator();
		while(itr.hasNext()) {
			ReservationVO reservation = itr.next();
			if(reservation==null) {
				return null;
			}
			reservation.setRoom_representing_image_res();
		}
		return reservationList;
	}
	
	@Override
	public int getInsertableCheck(ReservationVO vo) {
		// TODO Auto-generated method stub
		return reservationMapper.getInsertableCheck(vo);
	}
	
	@Override
	public List<ReservationVO> getRoomReservationList(int room_seq) {
		// TODO Auto-generated method stub
		return reservationMapper.getRoomReservationList(room_seq);
	}
	
	@Override
	public List<ReservationVO> getRoomDateReservationList(ReservationVO vo) {
		// TODO Auto-generated method stub
		return reservationMapper.getRoomDateReservationList(vo);
	}
	
	@Override
	public List<ReservationVO> getRoomAfterNowReservationList(int room_seq) {
		// TODO Auto-generated method stub
		return reservationMapper.getRoomAfterNowReservationList(room_seq);
	}
	
	@Override
	public int getAfterNowReservationCount(int room_seq) {
		// TODO Auto-generated method stub
		return reservationMapper.getAfterNowReservationCount(room_seq);
	}
	
	@Override
	public void deleteReservation(int reservation_seq) {
		// TODO Auto-generated method stub
		reservationMapper.deleteReservation(reservation_seq);
	}
	
	@Override
	public int getReservationSeq(ReservationVO vo) {
		// TODO Auto-generated method stub
		return reservationMapper.getReservationSeq(vo);
	}
	
	@Override
	public ReservationVO getReservation(int reservation_seq) {
		// TODO Auto-generated method stub
		return reservationMapper.getReservation(reservation_seq);
	}
	
	@Override
	public void deleteUnpaidReservation(int reservation_seq) {
		// TODO Auto-generated method stub
		reservationMapper.deleteUnpaidReservation(reservation_seq);
	}
}
