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
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        // 세션 사용 안함 -> (JWT 사용)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // URL별 권한 설정해야돼 이거안하면안댄다
        .authorizeHttpRequests(auth -> auth
            // 정적 리소스 및 공개 경로 모두 허용 -> 이거 나중에 캐싱설정가능해
            .requestMatchers(
                "/api/**",
                "/commu-images/**",
                "/join", 
                "/", 
                "/css/**", 
                "/img/**", 
                "/js/**", 
                "/favicon.ico",
                "/service-worker.js",
                "/error"
            ).permitAll()
            // USER 권한 필요
            .requestMatchers("/api/user/**").hasRole("USER")
            // 나머지 요청은 인증 필요함
            .anyRequest().authenticated()
        )
        // JWT 필터 UsernamePasswordAuthenticationFilter 이전에 추가해야대
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable);
        
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsSvc);
    }
}
