package com.seol.communityfeed.auth.application.domain;

import com.seol.communityfeed.auth.domain.RandomTokenGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomTokenGeneratorTest {

    @Test
    void generateToken_shouldReturnNonNullAndNonEmpty() {
        String token = RandomTokenGenerator.generateToken();

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void generateToken_shouldMatchExpectedPattern() {
        String token = RandomTokenGenerator.generateToken();

        // 예: 영문+숫자 20자
        assertTrue(token.matches("^[A-Za-z0-9]{20}$"));
    }

    @Test
    void generateToken_shouldReturnUniqueValues() {
        String token1 = RandomTokenGenerator.generateToken();
        String token2 = RandomTokenGenerator.generateToken();

        assertNotEquals(token1, token2);
    }
}