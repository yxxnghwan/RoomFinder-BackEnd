package com.roomfinder.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class FileManagement {
	@Value("${storage_server_ip}")
    private static String storage_server_ip;
	
	@Value("${storage_server_ip}")
	public static void setStorage_server_ip(String storage_server_ip) {
		FileManagement.storage_server_ip = storage_server_ip;
	}
	
	public static String getStorage_server_ip() {
		return storage_server_ip;
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
			return null;
		}
		String filePath = path + "/" + "store_representing_image" + extension;
	    File destination = new File(filePath);
	    String url = "http://"+storage_server_ip + "/roomfinderFiles/"+ store_email + "/" + destination.getName();
	    file.transferTo(destination); // 파일 업로드 작업 수행
	    System.out.println("경로 : " + url);
	    return url;
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
			return null;
		}
		String filePath = path + "/" + System.currentTimeMillis() + extension;
	    File destination = new File(filePath);
	    String url = "http://"+storage_server_ip + "/roomfinderFiles/"+ store_email + "/" + destination.getName();
	    file.transferTo(destination); // 파일 업로드 작업 수행
	    System.out.println("경로 : " + url);
	    return url;
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
}
