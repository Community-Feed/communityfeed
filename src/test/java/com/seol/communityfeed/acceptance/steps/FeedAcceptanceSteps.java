package com.seol.communityfeed.acceptance.steps;

import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

import io.restassured.response.Response;
import io.restassured.response.ExtractableResponse;

import java.util.List;

import java.util.Map;


public class FeedAcceptanceSteps {

    public static class LoginInfo {
        public String accessToken;
        public Long userId;
    }


    public static LoginInfo registerAndGetLoginInfo(String email, String password) {
        // 1. 이메일 인증 요청
        RestAssured
                .given()
                .body(Map.of("email", email))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/signup/send-verification-email")
                .then()
                .statusCode(200);

        // 2. 이메일 인증 확인 (테스트에서는 고정된 토큰 사용: 예: "123456")
        RestAssured
                .given()
                .queryParam("email", email)
                .queryParam("token", "123456")
                .when()
                .get("/signup/verify-token")
                .then()
                .statusCode(200);

        // 3. 회원가입
        RestAssured
                .given()
                .body(Map.of("email", email, "password", password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/signup/register")
                .then()
                .statusCode(200);

        // 4. 로그인 후 토큰 저장
        ExtractableResponse<Response> response = RestAssured
                .given()
                .body(Map.of("email", email, "password", password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract();

        String token = response.jsonPath().getString("value.accessToken");
        if (token == null) {
            System.out.println("로그인 응답에 accessToken 없음: " + response.asString());
        }

        // 5. 로그인 후 유저 정보 가져오기
        Long userId = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/user/me")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("value.id");

        LoginInfo info = new LoginInfo();
        info.accessToken = token;
        info.userId = userId;

        return info;
    }


    public static Long requestCreatePost(CreatePostRequestDto dto, String token) {
        var response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/post");

        if (response.statusCode() != 200) {
            System.out.println("Failed to create post. Status: " + response.statusCode());
            return null;
        }

        return response.then().extract().jsonPath().getLong("value");
    }

    public static List<GetPostContentResponseDto> requestFeed(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .getList("value", GetPostContentResponseDto.class);
    }

    public static Integer requestFeedCode(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .get("code");
    }

    public static void requestFollow(Long targetUserId, String token) {
        RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/follow/" + targetUserId)
                .then()
                .statusCode(200);
    }
}