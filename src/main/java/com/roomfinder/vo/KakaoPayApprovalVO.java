package com.roomfinder.vo;

import lombok.Data;

@Data
public class KakaoPayApprovalVO {
	
	
	
	//response
	private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private KakaoPayAmountVO amount;
    private KakaoPayCardVO card_info;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private Data created_at, approved_at;
}
