package com.roomfinder.controller;

import java.io.File;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.config.FileManagement;
import com.roomfinder.service.ReservationService;
import com.roomfinder.service.RoomService;
import com.roomfinder.vo.AccountVO;
import com.roomfinder.vo.RoomImageVO;
import com.roomfinder.vo.RoomVO;
import com.roomfinder.vo.StoreVO;

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
	public void postRoom(HttpServletRequest request, HttpServletResponse response, @ModelAttribute @RequestBody RoomVO vo) {
		System.out.println("postRoom 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면	
			
			// 룸 디렉토리 생성
			String roomDirectory = "room_" + System.currentTimeMillis();
			String path = FileManagement.getResource_directory_path() + "/" + vo.getStore_email() + "\\" + roomDirectory; //폴더 경로
			File folder = new File(path);
			String representing_image_extension = null;
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
			
			//대표 이미지 저장
			try {
				representing_image_extension = FileManagement.uploadRoomRepresentingImage(vo.getRoom_representing_image(), path, vo.getStore_email(), roomDirectory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("파일입출력 에러");
				return;
			}
			vo.setDirectory_name(roomDirectory);
			vo.setRepresenting_image_extension(representing_image_extension);
			// 디비추가

			try {
				roomService.insertRoom(vo);
				response.setStatus(HttpStatus.CREATED.value());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("리소스 생성됐는데 디비 넣다 에러났음 그래서 디렉토리 지워버릴거");
				FileManagement.deleteDirectory(path);
				response.setStatus(HttpStatus.BAD_REQUEST.value());
			}
			
		} else {
			System.out.println("본인만 스터디룸 등록 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 스터디룸 이미지 등록 */
	@PostMapping("/image")
	public void postRoomImage(HttpServletRequest request, HttpServletResponse response, @ModelAttribute @RequestBody RoomImageVO vo) {
		System.out.println("postRoomImage 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		RoomVO room = roomService.getRoom(vo.getRoom_seq());
		if(account.getEmail().equals(room.getStore_email())) { // 로그인 된 본인이면	
			
			// 룸 디렉토리 생성
			String path = FileManagement.getResource_directory_path() + "/" + room.getStore_email() + "\\" + room.getDirectory_name(); //폴더 경로
			File folder = new File(path);
			String file_name = null;
			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
			if (!folder.exists()) {
				try{
				    folder.mkdir(); //폴더 생성합니다.
				    System.out.println(room.getStore_email() + "의 폴더가 생성되었습니다.");
			        } 
			        catch(Exception e){
				    e.getStackTrace();
				}        
		         }else {
				System.out.println("이미 폴더가 생성되어 있습니다.");
			}
			
			try {
				file_name = FileManagement.uploadRoomImage(vo.getRoom_image(), path, room.getStore_email(), room.getDirectory_name());
				vo.setFile_name(file_name);
				System.out.println("이미지 업로드 성공");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("이미지 업로드 에러");
				return;
			}
			
			try {
				roomService.insertRoomImage(vo);
				response.setStatus(HttpStatus.CREATED.value());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("리소스업로드는 했는데 디비에서 에러났어 그래서 리소스 지워줄거임");
				FileManagement.deleteFile(path+"/"+file_name);
			}
			
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
		System.out.println(vo);
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
	
	/** 룸 정보 수정 */
	@PutMapping
	public void updateRoom(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomVO vo) {
		System.out.println("updateRoom");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		if(account.getEmail().equals(vo.getStore_email())) { // 로그인 된 본인이면	
			roomService.updateRoom(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 스터디룸 정보수정 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 룸 대표 이미지 변경 */
	@PatchMapping("/representingimage")
	public void patchRoomRepresentingImage(HttpServletRequest request, HttpServletResponse response, @ModelAttribute @RequestBody RoomVO vo) {
		System.out.println("patchRoomRepresentingImage 요청");
		System.out.println(vo);
		AccountVO account = (AccountVO)request.getAttribute("account");
		// 룸 불러오기
		RoomVO room = roomService.getRoom(vo.getRoom_seq());
		if(account.getEmail().equals(room.getStore_email())) { // 로그인 된 본인이면	
			
			//기존 이미지 삭제
			String path = FileManagement.getResource_directory_path() + "/" + room.getStore_email() + "/" + room.getDirectory_name() + "/room_representing_image" + room.getRepresenting_image_extension();
			FileManagement.deleteFile(path);
			
			// 새 이미지 추가
			path = FileManagement.getResource_directory_path() + "/" + room.getStore_email() + "/" + room.getDirectory_name();
			String newExtension = null;
			try {
				newExtension = FileManagement.uploadRoomRepresentingImage(vo.getRoom_representing_image(), path, room.getStore_email(), room.getDirectory_name());
				System.out.println("새 이미지 업로드 성공");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				return;
			}
			
			//DB수정
			vo.setRepresenting_image_extension(newExtension);
			roomService.updateRoomRepresentingImage(vo);
			response.setStatus(HttpStatus.OK.value());
		} else {
			System.out.println("본인만 스터디룸 정보수정 가능");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	/** 룸삭제 */
	@DeleteMapping
	public void deleteRoom(HttpServletRequest request, HttpServletResponse response, @RequestBody RoomVO vo) {
		System.out.println("deleteRoom 요청");
		System.out.println(vo);
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
