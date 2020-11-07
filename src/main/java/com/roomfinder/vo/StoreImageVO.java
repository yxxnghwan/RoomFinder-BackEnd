package com.roomfinder.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public @Data class StoreImageVO {
	private int store_image_seq;
	private String store_image_res;
	private String store_email;
	private MultipartFile store_image;
	private String file_name;
}
