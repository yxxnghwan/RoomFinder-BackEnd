package com.roomfinder.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public @Data class RoomVO {
	private int room_seq;
	private String room_name;
	private int price_per_hour;
	private String description;
	private int capacity;
	private String room_representing_image_res;
	private String directory_name;
	private String store_email;
	private MultipartFile room_representing_image;
	private List<RoomImageVO> room_image_list;
	private String representing_image_extension;
}
