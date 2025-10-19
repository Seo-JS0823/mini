package com.liferuner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.liferuner.security.CustomUserDetailsService;
import com.liferuner.security.JwtTokenProvider;
import com.liferuner.security.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService userDetailsSvc;
	
	public SecurityConfig(JwtTokenProvider jwtTokenProvider,
			              CustomUserDetailsService userDetailsSvc) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsSvc = userDetailsSvc;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		// CSRF 비활성화
		.csrf(AbstractHttpConfigurer::disable)
		// 세션을 사용하지 않음
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		// 권한 설정
		.authorizeHttpRequests(auth -> auth
			// 권한 인증, 회원가입, 로그인 등 경로 허용
			.requestMatchers("/api/auth/**", "/join", "/", "/css/**", "/img/**", "/js/**", "/favicon.ico").permitAll()
			// USER 권한 필요한 경로
			.requestMatchers("/api/user/**").hasRole("USER")
			// 나머지 모든 요청은 인증 필요(토큰)
			.anyRequest().authenticated()
		)
		// JWT 필터 등록
		// JWT 검증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가한다.
		.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		// 기본 폼 로그인, HTTP Basic 비활성화
		.formLogin(AbstractHttpConfigurer::disable)
		.httpBasic(AbstractHttpConfigurer::disable);
		
		return http.build();
	}
	
	// PasswordEncoder 빈 등록 (비밀번호 해싱/검증에 사용)
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// AuthenticationManager 빈 등록 (로그인 시 사용)
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// JWT 필터를 빈으로 등록하여 주입 가능하게 함
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsSvc);
	}
}
