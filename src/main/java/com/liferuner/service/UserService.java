package com.liferuner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liferuner.entity.User;
import com.liferuner.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	public String joinExecute(User user) {
		User join = repo.save(user);
		if(join == null) return "회원가입 실패";
		return "회원가입 성공";
	}
	
	public boolean isValidUser(User user) {
		User login = repo.findByUseridAndPassword(user.getUserid(), user.getPassword());
		if(login == null) return false;
		return true;
	}
}
