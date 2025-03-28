package com.seol.communityfeed.acceptance.steps;

import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

import java.util.List;

public class FeedAcceptanceSteps {

    public static Long requestCreatePost(CreatePostRequestDto dto) {
        var response = RestAssured
                .given()
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
                .header("Authorization","Bearer"+ token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .getList("value", GetPostContentResponseDto.class); // ✅ getList 사용
    }


    public static Integer requestFeedCode(String token){
        return RestAssured
                .given().log().all()
                .header("Authorization", "Barer"+token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .get("code");
    }
}