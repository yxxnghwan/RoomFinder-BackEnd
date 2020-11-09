package com.roomfinder.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.roomfinder.service.PaymentService;
import com.roomfinder.vo.KakaoPayApprovalVO;
import com.roomfinder.vo.KakaoPayReadyVO;
import com.roomfinder.vo.PaymentVO;

import lombok.extern.java.Log;

@Service
@Log
public class KakaoPay {
	private static final String HOST = "https://kapi.kakao.com";
    
    @Value("${kakao_api_key}")
    private String kakao_api_key;
    
    @Value("${this_server_end_point}")
    private String this_server_end_point;
    
    public KakaoPayReadyVO kakaoPayReady(KakaoPayReadyVO requestVO) {
        RestTemplate restTemplate = new RestTemplate();
        KakaoPayReadyVO responseVO;
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "admin key를 넣어주세요~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!");
        headers.add("Accept", "application/json;charset=UTF-8");
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME"); // 테스트 cid 고정
        params.add("partner_order_id", requestVO.getPartner_order_id());
        params.add("partner_user_id", requestVO.getPartner_user_id());
        params.add("item_name", requestVO.getItem_name());
        params.add("quantity", "1"); // 이 서비스에선 주문수량 1 고정
        params.add("total_amount", requestVO.getTotal_amount());
        params.add("tax_free_amount", "0"); // 이 서비스는 면세 ㄴㄴ
        params.add("approval_url", "http://" + this_server_end_point + "/api/kakaopay/success/" + requestVO.getReservation_seq());
        params.add("cancel_url", "http://" + this_server_end_point + "/api/kakaopay/cancel/" + requestVO.getReservation_seq());
        params.add("fail_url", "http://" + this_server_end_point + "/api/kakaopay/fail/" + requestVO.getReservation_seq());
 
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
 
        try {
        	responseVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
           
            log.info("" + responseVO);
            return responseVO;
 
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, KakaoPayReadyVO readyVO) {
    	KakaoPayApprovalVO responseVO;
        log.info("KakaoPayInfo............................................");
        log.info("-----------------------------");
        
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "admin key를 넣어주세요~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!");
        headers.add("Accept", "application/json;charset=UTF-8");
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
 
        // 서버로 요청할 Body
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", readyVO.getTid());
        params.add("partner_order_id", readyVO.getPartner_order_id());
        params.add("partner_user_id", readyVO.getPartner_user_id());
        params.add("pg_token", pg_token);
        params.add("total_amount", readyVO.getTotal_amount());
        
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        
        try {
        	responseVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + responseVO);
          
            return responseVO;
        
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
}
