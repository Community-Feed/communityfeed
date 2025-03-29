package com.seol.communityfeed.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {

   /* private final SecretKey secretKey;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간


    public TokenProvider(@Value("${secret-key}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
    }*/

    private final SecretKey secretKey;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60;

    public TokenProvider(SecretKey secretKey) {
        this.secretKey = secretKey;
    }


    public String createToken(Long userId, String role) {
        Claims claims = Jwts.claims().subject(userId.toString()).build();

        Date now = new Date();
        Date expiry = new Date(now.getTime() + TOKEN_VALID_TIME);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiry)
                .claim("role", role)
                .signWith(secretKey)
                .compact();
    }

    public Long getUserId(String token) {
        return Long.parseLong(
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject()
        );
    }

    public String getUserRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
