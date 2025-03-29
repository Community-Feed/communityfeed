package com.seol.communityfeed.acceptance.steps;

import com.seol.communityfeed.auth.application.dto.LoginRequestDto;
import com.seol.communityfeed.auth.application.dto.UserAccessTokenResponseDto;
import io.restassured.RestAssured;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.MediaType;

public class LoginAcceptanceSteps {

    public static String requestLoginGetToken(LoginRequestDto dto) {
        var response = RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login") // 로그인 API 경로가 맞는지 확인
                .then().log().all()
                .extract()
                .response();

        if (response.statusCode() != 200) {
            System.out.println("❌ 로그인 실패! Status code: " + response.statusCode());
            return null;
        }

        UserAccessTokenResponseDto res = response.as(UserAccessTokenResponseDto.class);
        if (res == null) {
            System.out.println("❌ 응답 바디를 UserAccessTokenResponseDto로 파싱 실패!");
            return null;
        }

        return res.accessToken();
    }

    public static Integer requestLoginGetResponseCode(LoginRequestDto dto){
        return RestAssured
                .given()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("login")
                .then()
                .extract()
                .jsonPath()
                .getObject("code", Integer.class);
    }

}
