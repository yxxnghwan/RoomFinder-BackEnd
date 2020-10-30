package com.roomfinder.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.roomfinder.vo.RoomImageVO;
import com.roomfinder.vo.RoomVO;

@Mapper
public interface RoomMapper {
	//C
	public void insertRoom(RoomVO vo);
	public void insertRoomImage(RoomImageVO vo);
	//R
	public RoomVO getRoom(int room_seq);
	//U
		
	//D
}
