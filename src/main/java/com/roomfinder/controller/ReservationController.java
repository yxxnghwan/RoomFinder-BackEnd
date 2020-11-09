package com.roomfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.service.PaymentService;
import com.roomfinder.service.ReservationService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.ReservationVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	PaymentService paymentService;
	
	class DeleteUnpaidReservationThread implements Runnable {
		
		int reservation_seq;
		
		public DeleteUnpaidReservationThread(int reservation_seq) {
			// TODO Auto-generated constructor stub
			this.reservation_seq = reservation_seq;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000*60*16);// 16분 뒤에
				System.out.println(reservation_seq + "번 예약 결제 체크");
				reservationService.deleteUnpaidReservation(reservation_seq); // 15분째 결제 안하는 친구 삭제
				
				// 결제팝업이 생기지도 않은 경우
				if(paymentService.getPayment(reservation_seq) == null) {
					reservationService.deleteReservation(reservation_seq);
					System.out.println("결제팝업이 생기기 전에 창을 닫아서 예약정보만 들어가고 결제팝업이 안열렸네 그래서 이친구 삭제");
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	private boolean isInsertableReservation(ReservationVO vo) {
		int insertableInt = reservationService.getInsertableCheck(vo);
		if(insertableInt > 0) {
			return false;
		}
		if(vo.getStart_time().isAfter(vo.getEnd_time())) {
			System.out.println("스타트타임이 어케 엔드보다 높냐");
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	/** 예약정보 추가 */
	@PostMapping // 여기서 getReservationSeq쿼리문 만들어서 seq값을 리턴해주자! 그럼 결제 요청할 때 같이 넣어서 주는거지!
	public int insertReservation(HttpServletRequest request, HttpServletResponse response, @RequestBody ReservationVO vo) {
		System.out.println("insertReservation 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getUser_email())) { // 로그인 된 본인이면
			if(isInsertableReservation(vo)) {
				reservationService.insertReservation(vo);
				
				int reservation_seq = reservationService.getReservationSeq(vo);
				DeleteUnpaidReservationThread deleteUnpaidReservationThread = new DeleteUnpaidReservationThread(reservation_seq);
				Thread thread = new Thread(deleteUnpaidReservationThread, "노결제 예약 삭제 쓰레드");
				thread.start();
				response.setStatus(HttpStatus.CREATED.value());
				return reservation_seq;
			} else {
				response.setStatus(HttpStatus.CONFLICT.value());
			}
		} else {
			System.out.println("본인만 예약 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		return 0;
	}
	
	/** 나의 예약정보 */
	@GetMapping("list")
	public List<ReservationVO> getMyReservationList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getMyReservationList 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		return reservationService.getMyReservation(account.getEmail());
	}
	
	/** 해당 룸의 예약정보 */
	@GetMapping("room/{room_seq}")
	public List<ReservationVO> getRoomReservationList(HttpServletRequest request, HttpServletResponse response, @PathVariable("room_seq") int room_seq) {
		System.out.println("getRoomReservationList 요청");
		return reservationService.getRoomReservationList(room_seq);
	}
	
	/** 해당 룸의 해당 날짜 예약정보 */
	@GetMapping("room/{room_seq}/{str_date}")
	public List<ReservationVO> getRoomDateReservationList(HttpServletRequest request, HttpServletResponse response, @PathVariable("room_seq") int room_seq, @PathVariable("str_date") String str_date) {
		System.out.println("getRoomDateReservationList요청");
		ReservationVO vo = new ReservationVO();
		vo.setRoom_seq(room_seq);
		vo.setStr_date(str_date);
		return reservationService.getRoomDateReservationList(vo);
	}
	
	/** 해당 룸의 현재시간 이후 예약정보 */
	@GetMapping("/room/afternow/{room_seq}")
	public List<ReservationVO> getRoomAfterNowReservationList(HttpServletRequest request, HttpServletResponse response, @PathVariable("room_seq") int room_seq) {
		System.out.println("getRoomAfterNowReservationList요청");
		return reservationService.getRoomAfterNowReservationList(room_seq);
	}
	
	/** 예약 삭제  */
	@DeleteMapping
	public void deleteReservation(HttpServletRequest request, HttpServletResponse response, @RequestBody ReservationVO vo) {
		System.out.println("deleteReservation 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getUser_email())) { // 로그인 된 본인이면
			reservationService.deleteReservation(vo.getReservation_seq());
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 삭제 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
}
