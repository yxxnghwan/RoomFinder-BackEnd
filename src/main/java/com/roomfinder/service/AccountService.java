package com.roomfinder.service;


import java.util.List;

import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.StoreVO;
import com.roomfinder.vo.UserVO;

public interface AccountService {

	// C
	public void insertAccount(AccountVO vo);
	public void insertUser(UserVO vo);
	public void insertStore(StoreVO vo);
	//R
	public AccountVO getAccount(AccountVO vo);
	public UserVO getUser(AccountVO vo);
	public StoreVO getStore(AccountVO vo);
	public List<UserVO> getUserList();
	public List<StoreVO> getStoreList();
	
	//U
	
	
	
	//D
}
