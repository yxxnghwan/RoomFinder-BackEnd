package com.roomfinder.vo;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

public @Data class KakaoPayReadyVO {
	//request
	String cid, partner_order_id, partner_user_id, item_name;
	Integer total_amount;
	String approval_url, cancel_url, fail_url;
	
	
	//response
	private String tid, next_redirect_pc_url;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created_at;
	
	//reservation정보
	private int reservation_seq;
}
