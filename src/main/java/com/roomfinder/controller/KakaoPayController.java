package com.roomfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.config.KakaoPay;
import com.roomfinder.mapper.RoomMapper;
import com.roomfinder.service.PaymentService;
import com.roomfinder.service.ReservationService;
import com.roomfinder.service.RoomService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.KakaoPayApprovalVO;
import com.roomfinder.vo.KakaoPayReadyVO;
import com.roomfinder.vo.PaymentVO;
import com.roomfinder.vo.ReservationVO;
import com.roomfinder.vo.RoomVO;

import lombok.extern.java.Log;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/kakaopay")
public class KakaoPayController {
	
	@Autowired
	private KakaoPay kakaopay;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired 
	RoomService roomService;
	
	@Autowired
	PaymentService paymentService;
    
    @PostMapping("/ready")
    public String kakaoPayReady(HttpServletRequest request, HttpServletResponse response, @RequestBody KakaoPayReadyVO vo) { // 파라미터로 KakaoPayReadyVO 를 받아오고 url을 응답해줄거니까 RestController로 바꾸자
        System.out.println("kakaoPayReady 요청");
        System.out.println(vo);
        AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getPartner_user_id())) { // 로그인 된 본인이면
			// api 요청
			KakaoPayReadyVO readyVO = kakaopay.kakaoPayReady(vo);
			// 응답받은 값으로 DB 데이터 세팅
			PaymentVO payment = new PaymentVO();
            payment.setReservation_seq(vo.getReservation_seq());
            payment.setTotal_price(vo.getTotal_amount());
            payment.setPayment_company_pay_id(readyVO.getTid());
            // db 저장
            try {
            	paymentService.insertPayment(payment);
    			return readyVO.getNext_redirect_pc_url(); // 데이터 리턴..? 아니면 그냥 리다이렉션..?
            } catch (Exception e) {
				// TODO: handle exception
            	System.out.println("예약은 추가됐는데 결제 넣다 에러남");
            	reservationService.deleteReservation(vo.getReservation_seq());
            	response.setStatus(HttpStatus.BAD_REQUEST.value());
			}
            
		} else {
			System.out.println("본인만 결제 가능");
			System.out.println("예약은 넣었는데 결제 실패했으니 예약정보 지울거임");
			reservationService.deleteReservation(vo.getReservation_seq());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
        return null; // 결제실패 url 넣기
    }
    
    @GetMapping("/success/{reservation_seq}")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, @PathVariable int reservation_seq) {
    	System.out.println("kakaoPaySuccess");
    	System.out.println("kakaoPaySuccess pg_token : " + pg_token);
    	
    	// 승인 API 요청 데이터 세팅
    	KakaoPayReadyVO readyVO = new KakaoPayReadyVO();
    	ReservationVO reservation = reservationService.getReservation(reservation_seq);
    	RoomVO room = roomService.getRoom(reservation.getRoom_seq());
    	PaymentVO payment = paymentService.getPayment(reservation_seq);
    	readyVO.setTid(payment.getPayment_company_pay_id());
    	readyVO.setPartner_order_id(room.getStore_email());
    	readyVO.setPartner_user_id(reservation.getUser_email());
    	readyVO.setTotal_amount(payment.getTotal_price());
    	// api 요청
    	KakaoPayApprovalVO approvalVO = kakaopay.kakaoPayInfo(pg_token, readyVO);
    	
        System.out.println(approvalVO);
        
        // 추가해논 db 업뎃
        payment.setPayment_method(approvalVO.getPayment_method_type());
        paymentService.updatePaymentMethod(payment);
    }
    
    @GetMapping("/cancel/{reservation_seq}")
    public void kakaoPayCancel(@PathVariable int reservation_seq) {
    	System.out.println("kakaoPayCancel");
    	System.out.println("취소했으니까 예약정보 지울거임");
    	reservationService.deleteReservation(reservation_seq);
    	paymentService.deletePayment(reservation_seq);
    }
    
    @GetMapping("/fail/{reservation_seq}")
    public void kakaoPayFail(@PathVariable int reservation_seq) {
    	System.out.println("kakaoPayFail");
    	System.out.println("실패했으니까 예약정보 지울거임");
    	reservationService.deleteReservation(reservation_seq);
    	paymentService.deletePayment(reservation_seq);
    }
}
