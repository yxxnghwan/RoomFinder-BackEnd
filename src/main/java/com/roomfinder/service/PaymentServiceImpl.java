package com.roomfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomfinder.mapper.PaymentMapper;
import com.roomfinder.vo.PaymentVO;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentMapper paymentMapper;
	
	@Override
	public void insertPayment(PaymentVO vo) {
		// TODO Auto-generated method stub
		paymentMapper.insertPayment(vo);
	}
	
	@Override
	public PaymentVO getPayment(int payment_seq) {
		// TODO Auto-generated method stub
		return paymentMapper.getPayment(payment_seq);
	}
	
	@Override
	public void updatePaymentMethod(PaymentVO vo) {
		// TODO Auto-generated method stub
		paymentMapper.updatePaymentMethod(vo);
	}
	
	@Override
	public void deletePayment(int payment_seq) {
		// TODO Auto-generated method stub
		paymentMapper.deletePayment(payment_seq);
	}
	
}
