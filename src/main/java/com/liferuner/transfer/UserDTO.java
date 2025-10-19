package com.liferuner.transfer;

import com.liferuner.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	private String userid;
	private String name;
	private String gender;
	private String birthday;
	private String password;
	private String email;
	private String address;
	
	public User toEntity() {
		return User.builder()
				   .userid(userid)
				   .name(name)
				   .gender(gender)
				   .birthday(birthday)
				   .password(password)
				   .email(email)
				   .address(address)
				   .build();
	}
}
