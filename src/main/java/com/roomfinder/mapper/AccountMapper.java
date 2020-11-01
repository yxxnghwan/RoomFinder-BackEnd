package com.roomfinder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.StoreVO;
import com.roomfinder.vo.UserVO;

@Mapper
public interface AccountMapper {
	
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
	public void deleteAccount(String email);
}
