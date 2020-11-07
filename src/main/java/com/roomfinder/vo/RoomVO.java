package com.roomfinder.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.roomfinder.config.FileManagement;

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
	
	public void setRoom_representing_image_res() {
		room_representing_image_res = "http://" + FileManagement.getStorage_server_end_point() + "/roomfinderFiles/" + this.store_email + "/" + this.directory_name + "/room_representing_image" + this.representing_image_extension;
	}
}
