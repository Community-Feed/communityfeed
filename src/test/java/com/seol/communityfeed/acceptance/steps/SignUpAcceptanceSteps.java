package com.seol.communityfeed.acceptance.steps;

import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class SignUpAcceptanceSteps {

    public static Integer requestSendEmail(SendEmailRequestDto dto){
        return RestAssured
                .given()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/signup/send-verification-email")
                .then()
                .extract()
                .jsonPath().get("code");
    }

    // 새로 추가: 인증 이메일 요청 후 토큰 받기
    public static String requestSendEmailAndGetToken(SendEmailRequestDto dto) {
        return RestAssured
                .given()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/signup/send-verification-email")
                .then()
                .extract()
                .jsonPath()
                .getString("value.token"); // 응답 구조에 따라 수정 필요
    }
}
