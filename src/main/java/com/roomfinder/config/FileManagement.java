package com.roomfinder.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class FileManagement {
	
	@Value("${storage_server_end_point}")
    private static String storage_server_end_point;
	
	@Value("${resource_directory_path}")
	private static String resource_directory_path;
	
	@Value("${storage_server_end_point}")
	public void setStorage_server_ip(String storage_server_ip) {
		this.storage_server_end_point = storage_server_ip;
	}
	
	public static String getStorage_server_end_point() {
		return storage_server_end_point;
	}

	public static String getResource_directory_path() {
		return resource_directory_path;
	}
	
	@Value("${resource_directory_path}")
	public void setResource_directory_path(String resource_directory_path) {
		FileManagement.resource_directory_path = resource_directory_path;
	}

	static public String uploadStoreRepresentingImage(MultipartFile file, String path, String store_email) throws Exception {
		String extension = "";
		String originalFileName = file.getOriginalFilename().toLowerCase();
		if(originalFileName.endsWith(".jpg")) {
			extension = ".jpg";
		} else if(originalFileName.endsWith(".jpeg")) {
			extension = ".jpeg";
		} else if(originalFileName.endsWith(".png")) {
			extension = ".png";
		} else {
			System.out.println("파일 확장자명 오류");
			return null;
		}
		String filePath = path + "/" + "store_representing_image" + extension;
	    File destination = new File(filePath);
	    //String url = "http://"+storage_server_end_point + "/roomfinderFiles/"+ store_email + "/" + destination.getName();
	    file.transferTo(destination); // 파일 업로드 작업 수행
	    return extension;
	}
	
	static public String uploadRoomRepresentingImage(MultipartFile file, String path, String store_email, String roomDirectory) throws Exception {
		String extension = "";
		String originalFileName = file.getOriginalFilename().toLowerCase();
		if(originalFileName.endsWith(".jpg")) {
			extension = ".jpg";
		} else if(originalFileName.endsWith(".jpeg")) {
			extension = ".jpeg";
		} else if(originalFileName.endsWith(".png")) {
			extension = ".png";
		} else {
			System.out.println("파일 확장자명 오류");
			return null;
		}
		String filePath = path + "/" + "room_representing_image" + extension;
	    File destination = new File(filePath);
	    //String url = "http://"+storage_server_end_point + "/roomfinderFiles/"+ store_email + "/" + roomDirectory + "/" + destination.getName();
	    file.transferTo(destination); // 파일 업로드 작업 수행
	    return extension;
	}

	static public String uploadStoreImage(MultipartFile file, String path, String store_email) throws Exception {
		String extension = "";
		String originalFileName = file.getOriginalFilename().toLowerCase();
		if(originalFileName.endsWith(".jpg")) {
			extension = ".jpg";
		} else if(originalFileName.endsWith(".jpeg")) {
			extension = ".jpeg";
		} else if(originalFileName.endsWith(".png")) {
			extension = ".png";
		} else {
			System.out.println("파일 확장자명 오류");
			return null;
		}
		String filePath = path + "/" + System.currentTimeMillis() + extension;
	    File destination = new File(filePath);
	    //String url = "http://"+storage_server_end_point + "/roomfinderFiles/"+ store_email + "/" + destination.getName();
	    file.transferTo(destination); // 파일 업로드 작업 수행
	    return destination.getName();
	}
	
	static public String uploadRoomImage(MultipartFile file, String path, String store_email, String roomDirectory) throws Exception {
		String extension = "";
		String originalFileName = file.getOriginalFilename().toLowerCase();
		if(originalFileName.endsWith(".jpg")) {
			extension = ".jpg";
		} else if(originalFileName.endsWith(".jpeg")) {
			extension = ".jpeg";
		} else if(originalFileName.endsWith(".png")) {
			extension = ".png";
		} else {
			return null;
		}
		String filePath = path + "/" + System.currentTimeMillis() + extension;
	    File destination = new File(filePath);
	    //String url = "http://"+storage_server_end_point + "/roomfinderFiles/"+ store_email + "/" + roomDirectory + "/" + destination.getName();
	    file.transferTo(destination); // 파일 업로드 작업 수행
	    return destination.getName();
	}
	
	static public void deleteDirectory(String directoryPath) {
		File folder = new File(directoryPath);
		try {
			while(folder.exists()) {
				File[] folder_list = folder.listFiles(); //파일리스트 얻어오기
						
				for (int j = 0; j < folder_list.length; j++) {
					folder_list[j].delete(); //파일 삭제 
					System.out.println("파일이 삭제되었습니다.");	
				}
						
				if(folder_list.length == 0 && folder.isDirectory()){ 
					folder.delete(); //대상폴더 삭제
					System.out.println("폴더가 삭제되었습니다.");
				}
			}
		 } catch (Exception e) {
			 e.getStackTrace();
		 }
	}
	
	static public void deleteFile(String path) {
		File file = new File(path);
		file.delete();
		System.out.println("파일 삭제 완료");
	}
}
