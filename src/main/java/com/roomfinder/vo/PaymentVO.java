package com.roomfinder.vo;

import lombok.Data;

public @Data class PaymentVO {
	private int payment_seq;
	private int reservation_seq;
	private int total_price;
	private String payment_method;
}
