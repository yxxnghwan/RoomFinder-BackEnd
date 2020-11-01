package com.roomfinder.service;

import java.util.List;

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
	public AccountVO getAccount(String email) {
		// TODO Auto-generated method stub
		return accountMapper.getAccount(email);
	}
	
	@Override
	public UserVO getUser(String email) {
		// TODO Auto-generated method stub
		return accountMapper.getUser(email);
	}
	
	@Override
	public StoreVO getStore(String email) {
		// TODO Auto-generated method stub
		return accountMapper.getStore(email);
	}
	
	@Override
	public List<UserVO> getUserList() {
		// TODO Auto-generated method stub
		return accountMapper.getUserList();
	}
	
	@Override
	public List<StoreVO> getStoreList() {
		// TODO Auto-generated method stub
		return accountMapper.getStoreList();
	}
	
	@Override
	public void updatePassword(AccountVO vo) {
		// TODO Auto-generated method stub
		accountMapper.updatePassword(vo);
	}
	
	@Override
	public void updatePhone(UserVO vo) {
		// TODO Auto-generated method stub
		accountMapper.updatePhone(vo);
	}
	
	@Override
	public void updateUserName(UserVO vo) {
		// TODO Auto-generated method stub
		accountMapper.updateUserName(vo);
	}
	
	@Override
	public void deleteAccount(String email) {
		// TODO Auto-generated method stub
		accountMapper.deleteAccount(email);
	}
}
