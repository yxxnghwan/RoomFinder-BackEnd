package com.roomfinder.service;

import com.roomfinder.vo.RoomImageVO;
import com.roomfinder.vo.RoomVO;

public interface RoomService {
	//C
	public void insertRoom(RoomVO vo);
	public void insertRoomImage(RoomImageVO vo);
	//R
	public RoomVO getRoom(int room_seq);
	//U
	
	//D

}
