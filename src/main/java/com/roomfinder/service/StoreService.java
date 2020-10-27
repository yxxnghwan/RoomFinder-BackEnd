package com.roomfinder.service;

import java.util.List;

import com.roomfinder.vo.StoreImageVO;
import com.roomfinder.vo.StoreVO;

public interface StoreService {
	//C
	public void insertStoreImage(StoreImageVO vo);
	//R
	public List<StoreVO> getStoreList();
	//U
	public void updateStoreRepresentingImage(StoreVO vo);
	//D
}