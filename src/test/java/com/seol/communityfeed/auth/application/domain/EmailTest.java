package com.seol.communityfeed.auth.application.domain;

import com.seol.communityfeed.auth.domain.Email;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    void givenEmailIsEmpty_whenCreate_thenThrowError(String email) {
        assertThrows(IllegalArgumentException.class, () -> Email.createEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid/@ab", "naver.com", "natty@naver", "안녕하세요.com"})
    void givenInvalidEmail_whenCreate_thenThrowError(String email) {
        assertThrows(IllegalArgumentException.class, () -> Email.createEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid@ab.com", "a@naver.com", "test@test.com"})
    void givenEmailValidWhenCreateThenReturnEmail(String email) {
        // when
        Email emailValue = Email.createEmail(email);

        // then
        assertEquals(email, emailValue.getEmailText());
    }
}
