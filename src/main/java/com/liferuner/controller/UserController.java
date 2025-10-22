package com.liferuner.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liferuner.entity.Board;
import com.liferuner.entity.User;
import com.liferuner.repository.BoardRepository;
import com.liferuner.repository.UserRepository;
import com.liferuner.transfer.UserDTO;

@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private BoardRepository boardRepo;
    
    @GetMapping("/")
    public String index() {
        return "core";
    }
    
    // 회원가입: 비밀번호 암호화 후 저장
    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody UserDTO userDTO) {
        User user = userDTO.toEntity();
        
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("USER");
        
        System.out.println(user.getUsername());
        User join = userRepo.save(user);
        if(join == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok("회원가입 성공");
    }
    
    @PostMapping("/api/commu/write")
    @ResponseBody
    public ResponseEntity<String> respo(@RequestBody ContentReq request) {
    	String uuid = null;
    	String html = request.getHtml();
    	if(request.getImgs() != null) {
    		for(BoardReq img : request.getImgs()) {
    			String mimeType = img.getMimeType().split("/")[1];
    			String base64Data = img.getBase64Data();
    			
    			byte[] imageByte = Base64.getDecoder().decode(base64Data);
    			uuid = UUID.randomUUID().toString() + "." + mimeType;
    			ImageUpload.fileLifeSave(uuid, imageByte);
    		}
    	}
    	User user = userRepo.findById(4L).orElse(null);
    	Board board = Board.builder().category("TEST").html(html).path(uuid).user(user).build();
    	board.setPath(board.getFilePath());
    	boardRepo.save(board);
    	
    	
    	
    	Board response = boardRepo.findById(252L).orElse(null);
    	
    	return ResponseEntity.ok(response.getImagePlusHtml());
    }
    
    @GetMapping("/commu-images/{name}")
    @ResponseBody
    public ResponseEntity<byte[]> commuImage(@PathVariable("name") String name) {
    	System.out.println("냠냠" + name);
    	Path filePath = Paths.get("commu-images/" + name);
    	
    	byte[] file = null;
		try {
			file = Files.readAllBytes(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return ResponseEntity.status(HttpStatus.OK)
	             .header("Content-Type", "image/jpeg")
	             .body(file);
    }
}



























