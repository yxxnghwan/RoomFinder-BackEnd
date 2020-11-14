package com.roomfinder.service;

import java.util.Iterator;
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
		List<StoreVO> storeList = storeMapper.getStoreList();
		Iterator<StoreVO> itr = storeList.iterator();
		while(itr.hasNext()) {
			StoreVO store = itr.next();
			if(store == null) {
				return null;
			}
			store.setStore_representing_image_res();
		}
		return storeList;
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
		StoreImageVO storeImage = storeMapper.getStoreImage(store_image_seq);
		storeImage.setStore_image_res();
		return storeImage;
	}
	
	@Override
	public void updateStore(StoreVO vo) {
		// TODO Auto-generated method stub
		storeMapper.updateStore(vo);
	}
	
	@Override
	public List<StoreVO> getPriceSearchStoreList(SearchVO vo) {
		// TODO Auto-generated method stub
		List<StoreVO> storeList = storeMapper.getPriceSearchStoreList(vo);
		Iterator<StoreVO> itr = storeList.iterator();
		while(itr.hasNext()) {
			StoreVO store = itr.next();
			if(store == null) {
				return null;
			}
			store.setStore_representing_image_res();
		}
		return storeList;
	}
	
	@Override
	public List<StoreVO> getTotalSearchStoreList(String search_keyword) {
		// TODO Auto-generated method stub
		List<StoreVO> storeList = storeMapper.getTotalSearchStoreList(search_keyword);
		Iterator<StoreVO> itr = storeList.iterator();
		while(itr.hasNext()) {
			StoreVO store = itr.next();
			if(store == null) {
				return null;
			}
			store.setStore_representing_image_res();
		}
		return storeList;
	}
	
	@Override
	public void updateStoreImageRes(StoreImageVO vo) {
		// TODO Auto-generated method stub
		storeMapper.updateStoreImageRes(vo);
	}
	
	@Override
	public List<StoreImageVO> getStoreImageList(String store_email) {
		// TODO Auto-generated method stub
		List<StoreImageVO> storeImageList = storeMapper.getStoreImageList(store_email);
		Iterator<StoreImageVO> itr = storeImageList.iterator();
		while(itr.hasNext()) {
			StoreImageVO storeImage = itr.next();
			if(storeImage == null) {
				return null;
			}
			storeImage.setStore_image_res();
		}
		return storeImageList;
	}
	
	@Override
	public List<StoreVO> getAndSearchStoreList(SearchVO vo) {
		// TODO Auto-generated method stub
		List<StoreVO> storeList = storeMapper.getAndSearchStoreList(vo);
		Iterator<StoreVO> itr = storeList.iterator();
		while(itr.hasNext()) {
			StoreVO store = itr.next();
			if(store == null) {
				return null;
			}
			store.setStore_representing_image_res();
		}
		return storeList;
	}
	
	@Override
	public List<StoreVO> getFlexibleSearchStoreList(SearchVO vo) {
		// TODO Auto-generated method stub
		List<StoreVO> storeList = storeMapper.getFlexibleSearchStoreList(vo);
		Iterator<StoreVO> itr = storeList.iterator();
		while(itr.hasNext()) {
			StoreVO store = itr.next();
			if(store == null) {
				return null;
			}
			store.setStore_representing_image_res();
		}
		return storeList;
	}
}
