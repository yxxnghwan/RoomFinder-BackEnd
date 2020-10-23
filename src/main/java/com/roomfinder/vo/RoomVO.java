package com.roomfinder.vo;

import lombok.Data;

public @Data class RoomVO {
	private int room_seq;
	private String room_name;
	private int price_per_hour;
	private String description;
	private int capacity;
	private String room_representing_imageRes;
	private String store_email;
}
