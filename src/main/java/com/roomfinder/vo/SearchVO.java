package com.roomfinder.vo;

import lombok.Data;

public @Data class SearchVO {
	private Integer min_price;
	private Integer max_price;
	private String search_keyword;
	private String str_date;
	private String str_start_time;
	private String str_start_date_time;
	
	public void setStr_start_date_time() {
		this.str_start_date_time = this.str_date + "T" + this.str_start_time;
	}
}
