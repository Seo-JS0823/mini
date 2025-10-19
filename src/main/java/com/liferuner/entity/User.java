package com.liferuner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Users")
public class User {

	@Id
	@Column(name = "userid", nullable = false, length = 30)
	private String userid;
	
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	@Column(name = "gender", nullable = false, length = 10)
	private String gender;
	
	@Column(name = "birthday", nullable = false, length = 12)
	private String birthday;
	
	@Column(name = "password", nullable = false, length = 130)
	private String password;
	
	@Column(name = "email", nullable = false, unique = true, length = 320)
	private String email;
	
	@Column(name = "address", nullable = false, length = 100)
	private String address;
	
}
