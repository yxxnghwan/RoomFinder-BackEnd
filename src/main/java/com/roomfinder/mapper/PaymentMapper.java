package com.roomfinder.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.roomfinder.vo.PaymentVO;

@Mapper
public interface PaymentMapper {
	//C
	public void insertPayment(PaymentVO vo);
	//R
	public PaymentVO getPayment(int payment_seq);
	//U
	public void updatePaymentMethod(PaymentVO vo);
	//D
	public void deletePayment(int payment_seq);
}
