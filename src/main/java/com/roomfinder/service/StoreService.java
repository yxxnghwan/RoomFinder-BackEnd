package com.roomfinder.service;

import java.util.List;

import com.roomfinder.vo.RoomVO;
import com.roomfinder.vo.SearchVO;
import com.roomfinder.vo.StoreImageVO;
import com.roomfinder.vo.StoreVO;

public interface StoreService {
	//C
	public void insertStoreImage(StoreImageVO vo);
	//R
	public List<StoreVO> getStoreList();
	public StoreImageVO getStoreImage(int store_image_seq);
	public List<StoreVO> getLocationSearchStoreList(String search_keyword);
	public List<StoreVO> getPriceSearchStoreList(SearchVO vo);
	public List<StoreVO> getStoreNameSearchStoreList(String search_keyword);
	public List<StoreVO> getTotalSearchStoreList(String search_keyword);
	public List<StoreImageVO> getStoreImageList(String store_email);
	//U
	public void updateStoreRepresentingImage(StoreVO vo);
	public void updateStore(StoreVO vo);
	public void updateStoreImageRes(StoreImageVO vo);
	//D
	public void deleteStoreImage(StoreImageVO vo);
}
