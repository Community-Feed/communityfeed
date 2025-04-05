package com.seol.communityfeed.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/index.html", "/login.html", "/signup/**",
                                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html",
                                "/posts.html", "/users.html" // HTML은 모두 열어두기
                        ).permitAll()
                        .requestMatchers("/api/**").authenticated() // API만 보호
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/process-login") // 🔄 로그인 처리 URL 변경
                        .defaultSuccessUrl("/posts.html", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/index.html")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**");
    }
}
