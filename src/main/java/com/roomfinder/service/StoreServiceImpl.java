package com.roomfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomfinder.mapper.StoreMapper;
import com.roomfinder.vo.StoreImageVO;

@Service("storeService")
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	StoreMapper storeMapper;
	
	@Override
	public void insertStoreImage(StoreImageVO vo) {
		// TODO Auto-generated method stub
		storeMapper.insertStoreImage(vo);
	}
}
