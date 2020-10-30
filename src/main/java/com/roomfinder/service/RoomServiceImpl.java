package com.roomfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomfinder.mapper.RoomMapper;
import com.roomfinder.vo.RoomImageVO;
import com.roomfinder.vo.RoomVO;

@Service("roomService")
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	RoomMapper roomMapper;
	
	@Override
	public void insertRoom(RoomVO vo) {
		// TODO Auto-generated method stub
		roomMapper.insertRoom(vo);
	}
	
	@Override
	public void insertRoomImage(RoomImageVO vo) {
		// TODO Auto-generated method stub
		roomMapper.insertRoomImage(vo);
	}
	
	@Override
	public RoomVO getRoom(int room_seq) {
		// TODO Auto-generated method stub
		return roomMapper.getRoom(room_seq);
	}
}