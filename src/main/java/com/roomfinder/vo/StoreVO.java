package com.roomfinder.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.roomfinder.config.FileManagement;

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
	private List<StoreImageVO> store_image_list;
	private String representing_image_extension;
	
	public void setStore_representing_image_res() {
		this.store_representing_image_res = "http://" + FileManagement.getStorage_server_end_point() + "/roomfinderFiles/" +this.email + "/store_representing_image" + this.representing_image_extension;
	}
}
