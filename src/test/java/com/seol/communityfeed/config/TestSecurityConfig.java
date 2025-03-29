package com.seol.communityfeed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("test")
public class TestSecurityConfig {

    // Spring Security 6+ 버전에서는 아래와 같이 람다식 방식으로 수정
    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // lambda 형식으로 변경
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                ); // lambda 형식으로 변경

        return http.build();
    }
}