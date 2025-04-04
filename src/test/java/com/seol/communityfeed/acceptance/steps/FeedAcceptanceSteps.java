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
        // 회원가입
        RestAssured
                .given()
                .body(Map.of("email", email, "password", password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/signup/register")
                .then()
                .statusCode(200);

        // 로그인
        ExtractableResponse<Response> response = RestAssured
                .given()
                .body(Map.of("email", email, "password", password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract();

        LoginInfo info = new LoginInfo();
        info.accessToken = response.jsonPath().getString("value.accessToken");

        // /me 엔드포인트로 userId 조회
        info.userId = RestAssured
                .given()
                .header("Authorization", "Bearer " + info.accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/user/me") // 이 엔드포인트가 있어야 함
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("value.id");

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