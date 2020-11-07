package com.roomfinder.vo;

import org.springframework.web.multipart.MultipartFile;

import com.roomfinder.config.FileManagement;

import lombok.Data;

public @Data class StoreImageVO {
	private int store_image_seq;
	private String store_image_res;
	private String store_email;
	private MultipartFile store_image;
	private String file_name;
	
	public void setStore_image_res() {
		this.store_image_res = "http://" + FileManagement.getStorage_server_end_point() + "/roomfinderFiles/" + this.store_email + "/" + this.file_name;
	}
}
