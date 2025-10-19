package com.liferuner.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Component
@Data
public class JwtTokenProvider {
	
	private final Key key;
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	public String createToken(String userid) {
		return Jwts.builder()
				   .setSubject(userid)
				   .setIssuedAt(new Date())
				   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				   .signWith(key)
				   .compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			    .setSigningKey(key)
			    .build()
			    .parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
	
	public String getUserId(String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(key).build()
				   .parseClaimsJws(token)
				   .getBody()
				   .getSubject();
	}
	
}
