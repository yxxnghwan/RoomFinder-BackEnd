package com.roomfinder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.roomfinder.vo.RoomVO;
import com.roomfinder.vo.StoreImageVO;
import com.roomfinder.vo.StoreVO;

@Mapper
public interface StoreMapper {
	
	//C
	public void insertStoreImage(StoreImageVO vo);
	//R
	public List<StoreVO> getStoreList();
	public StoreImageVO getStoreImage(int store_image_seq);
	public List<StoreVO> getLocationSearchStoreList(String search_keyword);
	//U
	public void updateStoreRepresentingImage(StoreVO vo);
	public void updateStore(StoreVO vo);
	//D
	public void deleteStoreImage(StoreImageVO vo);
}
