package com.roomfinder.vo;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.roomfinder.config.FileManagement;

import lombok.Data;

public @Data class ReservationVO {
	private int reservation_seq;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime start_time;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime end_time;
	private String user_email;
	private int room_seq;
	private String str_date;
	
	// 나의 예약내역시 추가할 데이터
	private String store_name;
	private String room_name;
	private String room_representing_image_res;
	private String representing_image_extension;
	private String directory_name;
	private String store_email;
	
	public void setRoom_representing_image_res() {
		room_representing_image_res = "http://" + FileManagement.getStorage_server_end_point() + "/roomfinderFiles/" + this.store_email + "/" + this.directory_name + "/room_representing_image" + this.representing_image_extension;
	}
	
}
