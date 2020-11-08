package com.roomfinder.service;

import java.util.Iterator;
import java.util.List;

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
		RoomVO room = roomMapper.getRoom(room_seq);
		room.setRoom_representing_image_res();
		return room;
	}
	
	@Override
	public List<RoomVO> getRoomList(String store_email) {
		// TODO Auto-generated method stub
		List<RoomVO> roomList = roomMapper.getRoomList(store_email);
		Iterator<RoomVO> itr = roomList.iterator();
		while(itr.hasNext()) {
			RoomVO room = itr.next();
			room.setRoom_representing_image_res();
		}
		return roomList;
	}
	
	@Override
	public void deleteRoomImage(int room_image_seq) {
		// TODO Auto-generated method stub
		roomMapper.deleteRoomImage(room_image_seq);
	}
	
	@Override
	public List<RoomImageVO> getRoomImageList(int room_seq) {
		// TODO Auto-generated method stub
		List<RoomImageVO> roomImageList = roomMapper.getRoomImageList(room_seq);
		Iterator<RoomImageVO> itr = roomImageList.iterator();
		while(itr.hasNext()) {
			RoomImageVO roomImage = itr.next();
			roomImage.setRoom_image_res();
		}
		return roomImageList;
	}
	
	@Override
	public void deleteRoom(int room_seq) {
		// TODO Auto-generated method stub
		roomMapper.deleteRoom(room_seq);
	}
	
	@Override
	public void updateRoom(RoomVO vo) {
		// TODO Auto-generated method stub
		roomMapper.updateRoom(vo);
	}
	
	@Override
	public void updateRoomRepresentingImage(RoomVO vo) {
		// TODO Auto-generated method stub
		roomMapper.updateRoomRepresentingImage(vo);
	}
}
