package com.roomfinder.service;

import java.util.List;

import com.roomfinder.vo.RoomVO;
import com.roomfinder.vo.StoreImageVO;
import com.roomfinder.vo.StoreVO;

public interface StoreService {
	//C
	public void insertStoreImage(StoreImageVO vo);
	public void insertRoom(RoomVO vo);
	//R
	public List<StoreVO> getStoreList();
	public StoreImageVO getStoreImage(int store_image_seq);
	//U
	public void updateStoreRepresentingImage(StoreVO vo);
	//D
	public void deleteStoreImage(StoreImageVO vo);
}
