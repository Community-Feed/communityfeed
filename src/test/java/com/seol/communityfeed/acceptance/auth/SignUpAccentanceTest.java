package com.seol.communityfeed.acceptance.auth;

import com.seol.communityfeed.acceptance.utils.AcceptanceTestTemplate;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.seol.communityfeed.acceptance.steps.SignUpAcceptanceSteps.requestSendEmail;
import static org.junit.jupiter.api.Assertions.*;

class SignUpAccentanceTest extends AcceptanceTestTemplate {

    private final String email = "email@gmail.com";

    @BeforeEach
    void setUp(){
        this.cleanUp();
    }

    @Test
    void givenEmail_whenSendEmail_thenVerificationTokenSaved(){
        //given
        SendEmailRequestDto dto = new SendEmailRequestDto(email);

        //when
        Integer code = requestSendEmail(dto);

        //then
        String token = this.getEmailToken(email);
        assertNotNull(token);
        assertEquals(0, code);
    }

    @Test
    void givenInvalidEmail_whenEmailSend_thenVerificationTokenNotSaved(){
        //given
        SendEmailRequestDto dto = new SendEmailRequestDto("abcd");

        //when
        Integer code = requestSendEmail(dto);

        //then
       // String token = this.getEmailToken(email);
       // assertNull(token);
        assertEquals(400, code);
    }
}
