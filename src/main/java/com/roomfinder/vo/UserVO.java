package com.roomfinder.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper=false)
public @Data class UserVO extends AccountVO {
	private String email;
	private String phone;
	private String user_name;
}
