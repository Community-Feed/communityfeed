package com.seol.communityfeed.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

  /*  @Value("${secret-key}")
    private String secret;

    @Bean
    public SecretKey secretKey() {
        // 문자열을 byte[]로 변환
        byte[] keyBytes = secret.getBytes();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
    }*/

    @Value("${secret-key}")
    private String secret;

    @Bean
    public SecretKey secretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}