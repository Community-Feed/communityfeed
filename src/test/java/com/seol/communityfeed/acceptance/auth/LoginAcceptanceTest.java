package com.seol.communityfeed.acceptance.auth;

import com.seol.communityfeed.acceptance.utils.AcceptanceTestTemplate;
import com.seol.communityfeed.auth.application.dto.CreateUserAuthRequestDto;
import com.seol.communityfeed.auth.application.dto.LoginRequestDto;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.seol.communityfeed.acceptance.steps.LoginAcceptanceSteps.requestLoginGetResponseCode;
import static com.seol.communityfeed.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginAcceptanceTest extends AcceptanceTestTemplate {
    private final String email = "email@gamil.com";

    @BeforeEach
    void setUp(){
        this.cleanUp();
        this.createUser(email);
    }

    @Test
    void givenEmailAndPassword_whenLogin_thenReturnToken(){
        //given
        LoginRequestDto dto = new LoginRequestDto(email, "password");

        //when
        String token = requestLoginGetToken(dto);

        //then
        assertNotNull(token);
    }

    @Test
    void givenEmailAndWrongPassword_whenLogin_thenReturnCodeNotZero(){
        //given
        LoginRequestDto dto = new LoginRequestDto(email,"password");

        //when
        Integer code = requestLoginGetResponseCode(dto);

        //then
        assertEquals(400, code);
    }
}
