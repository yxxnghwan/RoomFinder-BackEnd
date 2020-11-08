package com.roomfinder.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.roomfinder.config.FileManagement;
import com.roomfinder.service.AccountService;
import com.roomfinder.service.StoreService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.RoomVO;
import com.roomfinder.vo.SearchVO;
import com.roomfinder.vo.StoreImageVO;
import com.roomfinder.vo.StoreVO;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/store")
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	AccountService accountService;
	
	/** 매장이미지 등록 */
	@PostMapping("/image")
	public void postImage(HttpServletRequest request, HttpServletResponse response, @ModelAttribute @RequestBody StoreImageVO vo) {
		System.out.println("postImage 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면
			
			// 매장 디렉토리 생성
			String path = FileManagement.getResource_directory_path() + "/" + vo.getStore_email(); //폴더 경로
			File folder = new File(path);
			String file_name = null;
			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
			if (!folder.exists()) {
				try{
				    folder.mkdir(); //폴더 생성합니다.
				    System.out.println(vo.getStore_email() + "의 폴더가 생성되었습니다.");
			        } 
			        catch(Exception e){
				    e.getStackTrace();
				}        
		         }else {
				System.out.println("이미 폴더가 생성되어 있습니다.");
			}
			
			try {
				file_name = FileManagement.uploadStoreImage(vo.getStore_image(), path, vo.getStore_email());
				vo.setFile_name(file_name);
				System.out.println("이미지 업로드 성공");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("이미지 업로드 에러");
				return;
			}
			try {
				storeService.insertStoreImage(vo);
				response.setStatus(HttpStatus.OK.value());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("리소스업로드는 했는데 디비에서 에러났어 그래서 리소스 지워줄거임");
				FileManagement.deleteFile(path+"/"+file_name);
			}
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
	
	/** 대표 이미지 변경*/
	@PatchMapping("/representingimage")
	public void patchStoreRepresentingImage(HttpServletRequest request, HttpServletResponse response, @ModelAttribute @RequestBody StoreVO vo) {
		System.out.println("patchRepresentingImage 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getEmail())) { // 로그인 된 본인이면
			// 매장 불러오기
			StoreVO store = accountService.getStore(vo.getEmail());
			
			// 기존 이미지 삭제
			String path = FileManagement.getResource_directory_path() + "/" + store.getEmail() + "/store_representing_image" + store.getRepresenting_image_extension();
			FileManagement.deleteFile(path);
			
			// 새 이미지 추가
			path = FileManagement.getResource_directory_path() + "/" + store.getEmail();
			String newExtension = null;
			try {
				newExtension = FileManagement.uploadStoreRepresentingImage(vo.getStore_representing_image(), path, vo.getEmail());
				System.out.println("새 이미지 업로드 성공");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				return;
			}
			
			// DB 수정
			vo.setRepresenting_image_extension(newExtension);
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
	
	/** 매장 이미지 리스트 */
	@GetMapping("/image/list/{store_email}")
	public List<StoreImageVO> getStoreImageList(HttpServletRequest request, HttpServletResponse response, @PathVariable String store_email) {
		System.out.println("getStoreImageList 요청");
		return storeService.getStoreImageList(store_email);
	}
	
	/** 가격별 매장 검색 */
	@GetMapping("/price/{min_price}/{max_price}")
	public List<StoreVO> getPriceSearchStoreList(HttpServletRequest request, HttpServletResponse response, @PathVariable int min_price, @PathVariable int max_price) {
		System.out.println("getPriceSearchStoreList 요청");
		SearchVO vo = new SearchVO();
		vo.setMin_price(min_price);
		vo.setMax_price(max_price);
		return storeService.getPriceSearchStoreList(vo);
	}
	
	/** 지역이름 또는 매장이름 검색 */
	@GetMapping("/totalsearch/{search_keyword}")
	public List<StoreVO> getTotalSearchStoreList(HttpServletRequest request, HttpServletResponse response, @PathVariable String search_keyword) {
		System.out.println("getTotalSearchStoreList 요청");
		return storeService.getTotalSearchStoreList(search_keyword);
	}
	
	/** 가격 토탈 둘 다 충족 검색 */
	@GetMapping("/andsearch/{search_keyword}/{min_price}/{max_price}")
	public List<StoreVO> getAndSearchStoreList(HttpServletRequest request, HttpServletResponse response, @PathVariable("search_keyword") String search_keyword, @PathVariable("min_price") int min_price, @PathVariable("max_price") int max_price) {
		System.out.println("getAndSearchStoreList 요청");
		SearchVO vo = new SearchVO();
		vo.setSearch_keyword(search_keyword);
		vo.setMin_price(min_price);
		vo.setMax_price(max_price);
		return storeService.getAndSearchStoreList(vo);
	}
	
	/** 매장 전체정보 수정 */
	@PutMapping
	public void updateStore(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreVO vo) {
		System.out.println("updateStore 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getEmail())) { // 로그인 된 본인이면	
			storeService.updateStore(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 매장정보 수정 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 매장 상세 조회 */
	@GetMapping("/{email}")
	public StoreVO getStore(HttpServletRequest request, HttpServletResponse response, @PathVariable String email) {
		System.out.println("getStore 요청");
		StoreVO store = accountService.getStore(email);
		store.setStore_image_list(storeService.getStoreImageList(email));
		return store;
	}
	
	/** 매장 이미지 삭제 API */
	@DeleteMapping("/image")
	public void deleteStoreImage(HttpServletRequest request, HttpServletResponse response, @RequestBody StoreImageVO vo) {
		System.out.println("deleteStoreImage 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		StoreImageVO image = storeService.getStoreImage(vo.getStore_image_seq());
		if(account.getEmail().equals(image.getStore_email())) { // 로그인 된 본인이면	
			// 해당 레코드 불러오기
			StoreImageVO storeImage = storeService.getStoreImage(vo.getStore_image_seq());
			
			// 이미지 삭제
			String path = FileManagement.getResource_directory_path() + "/" + storeImage.getStore_email() + "/" + storeImage.getFile_name();
			FileManagement.deleteFile(path);
			
			//DB삭제
			storeService.deleteStoreImage(vo);
			response.setStatus(HttpStatus.OK.value());
			
		} else {
			System.out.println("본인만 매장 이미지 삭제 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
}
