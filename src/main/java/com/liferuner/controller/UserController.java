package com.liferuner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liferuner.entity.User;
import com.liferuner.entity.UserDTO;
import com.liferuner.repository.UserRepository;
import com.liferuner.security.JwtTokenProvider;
import com.liferuner.security.transfer.LoginRequestDTO;

@Controller
public class UserController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtTokenProvider jwt;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/")
	public String index() {
		return "user/home";
	}
	
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
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
		System.out.println(loginRequest.getPassword() + " / " + loginRequest.getUsername());
		User user = userRepo.findByUsername(loginRequest.getUsername())
				            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
		System.out.println("USER : " + user);
		boolean match = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
		
		System.out.println("비밀번호 일치 여부 : " + match);
		if(!match) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
		}
		
		UsernamePasswordAuthenticationToken authToken =
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		
		Authentication auth = authenticationManager.authenticate(authToken);
		
		String token = jwt.createToken(auth);
		System.out.println("TOKEN : " + token);
		return ResponseEntity.ok(token);
	}
}
