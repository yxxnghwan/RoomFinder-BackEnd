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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.service.StoreService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.RoomVO;
import com.roomfinder.vo.StoreImageVO;
import com.roomfinder.vo.StoreVO;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/store")
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	/** 매장이미지 등록 */
	@PostMapping("/image")
	public void postImage(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreImageVO vo) {
		System.out.println("postImage 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면
			storeService.insertStoreImage(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 이미지 추가 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 매장 전체 리스트 요청 */
	@GetMapping("/list")
	public List<StoreVO> getStoreList() {
		System.out.println("getStoreList 요청");
		return storeService.getStoreList();
	}
	
	/** 대표 이미지 추가  or 변경*/
	@PutMapping("/representingimage")
	public void putStoreRepresentingImage(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreVO vo) {
		System.out.println("putRepresentingImage 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getEmail())) { // 로그인 된 본인이면
			storeService.updateStoreRepresentingImage(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 대표 이미지 변경 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 매장 이미지 얻기 */
	@GetMapping("/image/{store_image_seq}")
	public StoreImageVO getStoreImage(HttpServletRequest request, HttpServletResponse response, @PathVariable int store_image_seq) {
		System.out.println("getStoreImage 요청");
		return storeService.getStoreImage(store_image_seq);
	}
	
	/** 매장 이미지 삭제 API */
	@DeleteMapping("/image")
	public void deleteStoreImage(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreImageVO vo) {
		System.out.println("deleteStoreImage 요청");
		AccountVO account = (AccountVO)request.getAttribute("account");
		StoreImageVO image = storeService.getStoreImage(vo.getStore_image_seq());
		if(account.getEmail().equals(image.getStore_email())) { // 로그인 된 본인이면	
			storeService.deleteStoreImage(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 매장 이미지 삭제 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
}
