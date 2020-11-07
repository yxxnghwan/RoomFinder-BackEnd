package com.roomfinder.vo;

import org.springframework.web.multipart.MultipartFile;

import com.roomfinder.config.FileManagement;

import lombok.Data;

public @Data class RoomImageVO {
	private int room_image_seq;
	private String room_image_res;
	private int room_seq;
	MultipartFile room_image;
	private String file_name;
	private String directory_name;
	private String email;
	
	public void setRoom_image_res() {
		this.room_image_res = "http://" + FileManagement.getStorage_server_end_point() + "/roomfinderFiles/" + this.email + "/" + this.directory_name + "/" + this.file_name;
	}
}
