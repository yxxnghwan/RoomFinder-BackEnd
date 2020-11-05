package com.roomfinder.controller;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/file")
public class FileController {
	
	@PostMapping
	public String uploadFile(@RequestParam("files") MultipartFile file) throws Exception {
	    String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
	    System.out.println("rootPath : " + rootPath);
	    String basePath = rootPath + "/" + "roomfinderFiles";
	    String filePath = basePath + "/" + "store_name_" +file.getOriginalFilename();
	    File destination = new File(filePath);
	    file.transferTo(destination); // 파일 업로드 작업 수행
	    return destination.getName();
	}
}
