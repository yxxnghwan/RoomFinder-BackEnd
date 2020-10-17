package com.roomfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomfinder.mapper.AccountMapper;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.StoreVO;
import com.roomfinder.vo.UserVO;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountMapper accountMapper;
	
	@Override
	public void insertAccount(AccountVO vo) {
		// TODO Auto-generated method stub
		accountMapper.insertAccount(vo);
	}
	
	@Override
	public void insertUser(UserVO vo) {
		// TODO Auto-generated method stub
		accountMapper.insertUser(vo);
	}
	
	@Override
	public void insertStore(StoreVO vo) {
		// TODO Auto-generated method stub
		accountMapper.insertStore(vo);
	}
	
	@Override
	public AccountVO getAccount(AccountVO vo) {
		// TODO Auto-generated method stub
		return accountMapper.getAccount(vo);
	}
	
	@Override
	public UserVO getUser(AccountVO vo) {
		// TODO Auto-generated method stub
		return accountMapper.getUser(vo);
	}
	
	@Override
	public StoreVO getStore(AccountVO vo) {
		// TODO Auto-generated method stub
		return accountMapper.getStore(vo);
	}
	
}
