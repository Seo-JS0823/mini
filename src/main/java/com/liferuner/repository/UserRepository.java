package com.liferuner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liferuner.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	public User findByUseridAndPassword(String userid, String password);
}
