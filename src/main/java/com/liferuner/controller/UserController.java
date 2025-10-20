package com.liferuner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liferuner.entity.User;
import com.liferuner.repository.UserRepository;
import com.liferuner.transfer.UserDTO;

@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepo;
    
    @GetMapping("/")
    public String index() {
        return "user/home";
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
}
