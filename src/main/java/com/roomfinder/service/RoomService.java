package com.roomfinder.service;

import java.util.List;

import com.roomfinder.vo.RoomImageVO;
import com.roomfinder.vo.RoomVO;

public interface RoomService {
	//C
	public void insertRoom(RoomVO vo);
	public void insertRoomImage(RoomImageVO vo);
	//R
	public RoomVO getRoom(int room_seq);
	public List<RoomVO> getRoomList(String store_email);
	//U
	
	//D
	public void deleteRoomImage(int room_image_seq);
}
