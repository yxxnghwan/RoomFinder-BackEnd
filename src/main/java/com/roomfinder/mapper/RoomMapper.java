package com.roomfinder.mapper;

import java.util.List;

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
	public List<RoomVO> getRoomList(String store_email);
	public List<RoomImageVO> getRoomImageList(int room_seq);
	//U
	public void updateRoom(RoomVO vo);
	//D
	public void deleteRoomImage(int room_image_seq);
	public void deleteRoom(int room_seq);
}
