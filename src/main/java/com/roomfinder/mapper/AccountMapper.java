package com.roomfinder.mapper;

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
	public AccountVO getAccount(AccountVO vo);
	public UserVO getUser(AccountVO vo);
	public StoreVO getStore(AccountVO vo);
	
	
	//U
	
	
	
	//D
}
