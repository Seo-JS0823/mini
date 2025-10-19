package com.liferuner.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liferuner.config.JwtTokenProvider;
import com.liferuner.entity.User;
import com.liferuner.service.UserService;
import com.liferuner.transfer.UserDTO;

@Controller
public class UserController {
	
	@Autowired
	private UserService svc;
	
	@Autowired
	private JwtTokenProvider jwt;
	
	@GetMapping("/")
	public String homeView() {
		return "user/home";
	}
	
	@PostMapping("/join")
	@ResponseBody
	public ResponseEntity<String> join(@RequestBody UserDTO target) {
		User entity = target.toEntity();
		System.out.println(entity);
		
		String response = svc.joinExecute(entity);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody UserDTO target) {
		User entity = target.toEntity();
		if(!svc.isValidUser(entity)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
		
		String token = jwt.createToken(entity.getUserid());
		
		return ResponseEntity.ok(Map.of("token", token));
	}
	
	@GetMapping("/finance")
	public String financeView() {
		return "finance/finance";
	}
	
}
