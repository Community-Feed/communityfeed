package com.seol.communityfeed.common.config;

import com.seol.communityfeed.auth.domain.TokenProvider;
import com.seol.communityfeed.common.principal.AuthPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
    private final TokenProvider tokenProvider;


    public AuthConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers){
        argumentResolvers.add(new AuthPrincipalArgumentResolver(tokenProvider));
    }
}
