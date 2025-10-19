package com.liferuner.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	
	private Long id;
	
	private String name;
	
	private String username;
	
	private String password;
	
	private String birthday;
	
	private String email;
	
	private String gender;
	
	private String address;
	
	private String role;
	
	public User toEntity() {
		return User.builder()
				   .id(id)
				   .name(name)
				   .username(username)
				   .password(password)
				   .birthday(birthday)
				   .email(email)
				   .gender(gender)
				   .address(address)
				   .role(role)
				   .build();
	}
}
