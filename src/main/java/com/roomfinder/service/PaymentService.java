package com.roomfinder.service;

import com.roomfinder.vo.PaymentVO;

public interface PaymentService {
	//C
	public void insertPayment(PaymentVO vo);
	//R
	public PaymentVO getPayment(int reservation_seq);
	//U
	public void updatePaymentMethod(PaymentVO vo);
	//D
	public void deletePayment(int reservation_seq);
}
