package com.roomfinder.vo;

import java.util.Date;

import lombok.Data;

public @Data class ReservationVO {
	private int reservation_seq;
	private Date start_time;
	private Date end_time;
	private String user_email;
	private int room_seq;
}
