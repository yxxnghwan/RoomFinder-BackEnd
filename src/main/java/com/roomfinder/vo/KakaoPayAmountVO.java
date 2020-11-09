package com.roomfinder.vo;

import lombok.Data;


public @Data class KakaoPayAmountVO {
	private Integer total, tax_free, vat, point, discount;
}
