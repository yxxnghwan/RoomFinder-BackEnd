package com.roomfinder.vo;

import lombok.Data;

public @Data class SearchVO {
	private int min_price;
	private int max_price;
	private String search_keyword;
}
