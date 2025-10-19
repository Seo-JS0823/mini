package com.liferuner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liferuner.security.JwtTokenProvider;
import com.liferuner.security.transfer.LoginRequestDTO;
import com.liferuner.security.transfer.TokenResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	
	public AuthController(AuthenticationManager authenticationManager,
			              JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenResponseDTO> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				                                    loginRequest.getPassword()
			)
		);
		
		String jwt = jwtTokenProvider.createToken(authentication);
		
		return ResponseEntity.ok(new TokenResponseDTO(jwt));
	}
}
