package com.liferuner.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	public String createToken(Authentication authentiacation) {
		String username = authentiacation.getName();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		
		return Jwts.builder()
				   .setSubject(username)
				   .setIssuedAt(now)
				   .setExpiration(expiryDate)
				   .signWith(key, SignatureAlgorithm.HS256)
				   .compact();
	}
	
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
				            .setSigningKey(key)
				            .build()
				            .parseClaimsJws(token)
				            .getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			    .setSigningKey(key)
			    .build()
			    .parseClaimsJws(token);
			return true;
		} catch(JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}
}
