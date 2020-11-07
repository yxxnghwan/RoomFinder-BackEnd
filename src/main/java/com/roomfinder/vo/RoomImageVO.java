package com.roomfinder.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public @Data class RoomImageVO {
	private int room_image_seq;
	private String room_image_res;
	private int room_seq;
	MultipartFile room_image;
	private String file_name;
}
