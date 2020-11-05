package com.roomfinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomfinder.mapper.StoreMapper;
import com.roomfinder.vo.RoomVO;
import com.roomfinder.vo.SearchVO;
import com.roomfinder.vo.StoreImageVO;
import com.roomfinder.vo.StoreVO;

@Service("storeService")
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	StoreMapper storeMapper;
	
	@Override
	public void insertStoreImage(StoreImageVO vo) {
		// TODO Auto-generated method stub
		storeMapper.insertStoreImage(vo);
	}
	
	@Override
	public List<StoreVO> getStoreList() {
		// TODO Auto-generated method stub
		return storeMapper.getStoreList();
	}
	
	@Override
	public void updateStoreRepresentingImage(StoreVO vo) {
		// TODO Auto-generated method stub
		storeMapper.updateStoreRepresentingImage(vo);
	}
	
	@Override
	public void deleteStoreImage(StoreImageVO vo) {
		// TODO Auto-generated method stub
		storeMapper.deleteStoreImage(vo);
	}
	
	@Override
	public StoreImageVO getStoreImage(int store_image_seq) {
		// TODO Auto-generated method stub
		return storeMapper.getStoreImage(store_image_seq);
	}
	
	@Override
	public void updateStore(StoreVO vo) {
		// TODO Auto-generated method stub
		storeMapper.updateStore(vo);
	}
	
	@Override
	public List<StoreVO> getLocationSearchStoreList(String search_keyword) {
		// TODO Auto-generated method stub
		return storeMapper.getLocationSearchStoreList(search_keyword);
	}
	
	@Override
	public List<StoreVO> getPriceSearchStoreList(SearchVO vo) {
		// TODO Auto-generated method stub
		return storeMapper.getPriceSearchStoreList(vo);
	}
	
	@Override
	public List<StoreVO> getStoreNameSearchStoreList(String search_keyword) {
		// TODO Auto-generated method stub
		return storeMapper.getStoreNameSearchStoreList(search_keyword);
	}
	
	@Override
	public List<StoreVO> getTotalSearchStoreList(String search_keyword) {
		// TODO Auto-generated method stub
		return storeMapper.getTotalSearchStoreList(search_keyword);
	}
}
