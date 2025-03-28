package com.seol.communityfeed.auth.application.domain;

import com.seol.communityfeed.auth.domain.Password;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void givenPassword_whenMatchSamePassword_thenReturnTrue() {
        Password password = Password.createEncryptPassword("password");

        assertTrue(password.matchPassword("password"));
    }

    @Test
    void givenPassword_whenMatchDiffPassword_thenReturnFalse() {
        Password password = Password.createEncryptPassword("password1");

        assertFalse(password.matchPassword("password"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenPasswordIsNullOrEmpty_thenThrowError(String password) {
        assertThrows(IllegalArgumentException.class, () -> Password.createEncryptPassword(password));
    }
}
