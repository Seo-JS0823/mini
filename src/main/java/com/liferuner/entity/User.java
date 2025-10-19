package com.liferuner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "user_id", unique = true, nullable = false)
	private String username;
	
	@Column(name = "user_pw", nullable = false)
	private String password;
	
	@Column(name = "user_birthday", nullable = false)
	private String birthday;
	
	@Column(name = "user_email", nullable = false)
	private String email;
	
	@Column(name = "user_gender", nullable = false)
	private String gender;
	
	@Column(name = "user_address", nullable = false)
	private String address;
	
	@Column(name = "user_role" , nullable = false)
	private String role;
	
	public void encodePassword(String encodedPassword) {
		this.password = encodedPassword;
	}
}
