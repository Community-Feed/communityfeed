package com.seol.communityfeed.acceptance.auth;

import com.seol.communityfeed.acceptance.steps.SignUpAcceptanceSteps;
import com.seol.communityfeed.acceptance.utils.AcceptanceTestTemplate;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignUpAccentanceTest extends AcceptanceTestTemplate {

    private final String email = "email@gmail.com";

    @BeforeEach
    void setUp() {
        this.cleanUp();
    }

    @Test
    void givenEmail_whenSendEmail_thenVerificationTokenSaved() {
        // given
        SendEmailRequestDto dto = new SendEmailRequestDto(email);

        // when
        Integer code = SignUpAcceptanceSteps.requestSendEmail(dto);

        // then
        String token = this.getEmailToken(email);
        assertNotNull(token);
        assertEquals(0, code);
    }

    @Test
    void givenInvalidEmail_whenEmailSend_thenVerificationTokenNotSaved() {
        // given
        SendEmailRequestDto dto = new SendEmailRequestDto("abcd");

        // when
        Integer code = SignUpAcceptanceSteps.requestSendEmail(dto);

        // then
        assertEquals(400, code);
    }

    @Test
    void givenEmailAndToken_whenVerify_thenEmailVerifiedTrue() {
        // given
        SendEmailRequestDto dto = new SendEmailRequestDto(email);
        SignUpAcceptanceSteps.requestSendEmail(dto);
        String token = this.getEmailToken(email);

        // when
        Integer code = SignUpAcceptanceSteps.requestVerifyEmail(email, token);

        // then
        assertEquals(0, code);
        assertTrue(this.isEmailVerified(email));
    }

    @Test
    void givenEmailAndWrongToken_whenVerify_thenEmailVerifiedFalse() {
        // given
        SendEmailRequestDto dto = new SendEmailRequestDto(email);
        SignUpAcceptanceSteps.requestSendEmail(dto);
        String wrongToken = "invalid-token";

        // when
        Integer code = SignUpAcceptanceSteps.requestVerifyEmail(email, wrongToken);

        // then
        assertEquals(400, code);
        assertFalse(this.isEmailVerified(email));
    }
}
