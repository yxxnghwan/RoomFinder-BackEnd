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

import com.roomfinder.service.ReservationService;
import com.roomfinder.service.RoomService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.RoomImageVO;
import com.roomfinder.vo.RoomVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/room")
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	ReservationService reservationService;
	
	private boolean isDeletableRoom(int room_seq) {
		if(reservationService.getAfterNowReservationCount(room_seq) > 0) {
			return false;
		}
		return true;
	}
	
	
	/** 스터디룸 등록 */
	@PostMapping
	public void postRoom(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomVO vo) {
		System.out.println("postRoom 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면	
			roomService.insertRoom(vo);
			response.setStatus(HttpStatus.CREATED.value());
			
		} else {
			System.out.println("본인만 스터디룸 등록 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 스터디룸 이미지 등록 */
	@PostMapping("/image")
	public void postRoomImage(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomImageVO vo) {
		System.out.println("postRoomImage 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		RoomVO room = roomService.getRoom(vo.getRoom_seq());
		if(account.getEmail().equals(room.getStore_email())) { // 로그인 된 본인이면	
			roomService.insertRoomImage(vo);
			response.setStatus(HttpStatus.CREATED.value());
			
		} else {
			System.out.println("본인만 스터디룸 이미지 등록 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 스터디룸 정보 */
	@GetMapping("/{room_seq}")
	public RoomVO getRoom(HttpServletRequest request, HttpServletResponse response, @PathVariable int room_seq) {
		System.out.println("getRoom 요청");
		return roomService.getRoom(room_seq);
	}
	
	/** 매장별 스터디룸 리스트 */
	@GetMapping("/list/{store_email}")
	public List<RoomVO> getRoomList(HttpServletRequest request, HttpServletResponse response, @PathVariable String store_email) {
		System.out.println("getRoomList 요청");
		return roomService.getRoomList(store_email);
	}
	
	/** 스터디룸 이미지 리스트 */
	@GetMapping("/image/list/{room_seq}")
	public List<RoomImageVO> getRoomImageList(HttpServletRequest request, HttpServletResponse response, @PathVariable int room_seq) {
		System.out.println("getRoomImageList 요청");
		return roomService.getRoomImageList(room_seq);
	}
	
	/** 스터디룸 이미지 삭제 */
	@DeleteMapping("/image")
	public void deleteRoomImage(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomImageVO vo) {
		System.out.println("deleteRoomImage 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		RoomVO room = roomService.getRoom(vo.getRoom_seq());
		if(account.getEmail().equals(room.getStore_email())) { // 로그인 된 본인이면	
			roomService.deleteRoomImage(vo.getRoom_image_seq());
			response.setStatus(HttpStatus.OK.value());
			
		} else {
			System.out.println("본인만 스터디룸 이미지 등록 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 룸삭제 */
	@DeleteMapping
	public void deleteRoom(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomVO vo) {
		System.out.println("deleteRoom 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면	
			if(isDeletableRoom(vo.getRoom_seq())) {
				roomService.deleteRoom(vo.getRoom_seq());
				response.setStatus(HttpStatus.OK.value());
			} else {
				response.setStatus(HttpStatus.CONFLICT.value());
			}
		} else {
			System.out.println("본인만 스터디룸 삭제 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
}
