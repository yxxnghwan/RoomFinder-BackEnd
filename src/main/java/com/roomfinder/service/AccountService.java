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
	public AccountVO getAccount(String email);
	public UserVO getUser(String email);
	public StoreVO getStore(String email);
	public List<UserVO> getUserList();
	public List<StoreVO> getStoreList();
	
	//U
	public void updatePassword(AccountVO vo);
	public void updatePhone(UserVO vo);
	public void updateUserName(UserVO vo);
	
	
	//D
}
