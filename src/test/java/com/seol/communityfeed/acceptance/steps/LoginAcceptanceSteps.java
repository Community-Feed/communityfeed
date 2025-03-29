package com.seol.communityfeed.acceptance.steps;

import com.seol.communityfeed.auth.application.dto.LoginRequestDto;
import com.seol.communityfeed.auth.application.dto.UserAccessTokenResponseDto;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

import java.util.Map;

public class LoginAcceptanceSteps {

    public static String requestLoginGetToken(LoginRequestDto dto) {
        var response = RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract()
                .response();

        // 실패 시 로깅
        if (response.statusCode() != 200) {
            System.out.println("❌ 로그인 실패! Status code: " + response.statusCode());
            return null;
        }

        // JsonPath로 accessToken 추출
        String token = response.jsonPath().getString("value.accessToken");

        if (token == null || token.isBlank()) {
            System.out.println("❌ 응답에 accessToken이 없음!");
            return null;
        }

        return token;
    }


    public static Integer requestLoginGetResponseCode(LoginRequestDto dto) {
        var response = RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract()
                .response();

        return response.jsonPath().getInt("code");
    }

}
