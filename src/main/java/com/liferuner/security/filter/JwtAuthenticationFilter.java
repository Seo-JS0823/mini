package com.liferuner.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.liferuner.security.CustomUserDetailsService;
import com.liferuner.security.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * 모든 요청을 가로채 Authorization 헤더에서 JWT 토큰을 추출하고 검증 후
 * 유요하면 SecurityContext에 인증 정보를 저장한다.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	private final CustomUserDetailsService userDetailsSvc;
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);
		
		String path = request.getServletPath();
		System.out.println("요청 Path : " + path);
		if(path.startsWith("/api/auth") || path.startsWith("/login") || path.startsWith("/join")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if(jwt != null && tokenProvider.validateToken(jwt)) {
			String username = tokenProvider.getUsernameFromToken(jwt);
			
			// 사용자 정보 로드
			UserDetails userDetails = userDetailsSvc.loadUserByUsername(username);
			
			// 인증 객체 생성
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
			
			// 요청 정보 설정
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			// 시큐리티컨텍스트에 인증 정보 저장 (로그인 상태 유지)
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
}
