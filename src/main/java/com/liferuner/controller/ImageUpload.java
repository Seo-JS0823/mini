package com.liferuner.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class ImageUpload {
	
	// 저장할 URL 생성
	public static String saveUrl(String originalFileName, String id) {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return "uploads/" + today + "-" + id + "-" + originalFileName;
	}
	
	// 저장할 파일 이름 생성
	public static String saveFileName(String originalFileName, String id) {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return today + "-" + id + "-" + originalFileName;
	}
	
	// 실제로 저장한 후 파일 이름 반환
	public static String fileSave(MultipartFile file, String fileName) {
		Path fileStorage = Paths.get("uploads").toAbsolutePath().normalize().resolve(fileName);
		
		try(InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, fileStorage, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			return null;
		}
		
		return fileName;
	}
	
	public static Map<String, Object> getImageData(Path url) {
		Map<String, Object> response = null;
		byte[] imageFile = null;
		String contentType = null;
		
		try {
			imageFile = Files.readAllBytes(url);
			contentType = Files.probeContentType(url);
		} catch (IOException e) {
			return null;
		}
		
		response = Map.of(
			"data", imageFile,
			"type", contentType
		);
		return response;
	}
	
	public static String fileLifeSave(String fileName, byte[] file) {
		Path fileStorage = Paths.get("commu-images").toAbsolutePath().normalize().resolve(fileName);
		try {
			Files.write(fileStorage, file);
		} catch (IOException e) {
			return "NO";
		}
		return "OK";
	}
	
}
