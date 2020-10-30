package com.roomfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.mapper.RoomMapper;
import com.roomfinder.service.RoomService;
import com.roomfinder.service.StoreService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.RoomImageVO;
import com.roomfinder.vo.RoomVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/room")
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	
	/** 스터디룸 등록 */
	@PostMapping("/")
	public void postRoom(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomVO vo) {
		System.out.println("postRoom 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면	
			roomService.insertRoom(vo);
			response.setStatus(HttpStatus.CREATED.value());
			
		} else {
			System.out.println("본인만 매장 이미지 삭제 가능");
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
			System.out.println("본인만 매장 이미지 삭제 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 스터디룸 정보 */
	@GetMapping("/{room_seq}")
	public RoomVO getRoom(HttpServletRequest request, HttpServletResponse response, @PathVariable int room_seq) {
		System.out.println("getRoom 요청");
		return roomService.getRoom(room_seq);
	}
}
