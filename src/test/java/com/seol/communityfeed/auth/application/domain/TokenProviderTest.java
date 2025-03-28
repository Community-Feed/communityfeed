package com.seol.communityfeed.auth.application.domain;

import com.seol.communityfeed.auth.domain.TokenProvider;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

class TokenProviderTest {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "testkeytestkeytestkeytestkeytestkeytestkeytestkeytestkey".getBytes(StandardCharsets.UTF_8)
    );

    private final TokenProvider tokenProvider = new TokenProvider(secretKey);

    @Test
    void givenValidUserAndRole_whenCreateToken_thenReturnValidToken() {
        Long userId = 1L;
        String role = "ADMIN";

        String token = tokenProvider.createToken(userId, role);

        assertNotNull(token);
        assertEquals(userId, tokenProvider.getUserId(token));
        assertEquals(role, tokenProvider.getUserRole(token));
    }

    @Test
    void givenInvalidToken_whenGetUserId_thenThrowException() {
        String invalidToken = "invalidToken";
        assertThrows(Exception.class, () -> tokenProvider.getUserId(invalidToken));
    }
}