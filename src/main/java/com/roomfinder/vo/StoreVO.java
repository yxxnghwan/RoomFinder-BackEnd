package com.roomfinder.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public @Data class StoreVO extends AccountVO {
	private String email;
	private String store_name;
	private String company_id;
	private String address_line;
	private String telephone;
	private String operating_time;
	private String description;
	private String store_representing_image_res;
	private MultipartFile store_representing_image;
}
