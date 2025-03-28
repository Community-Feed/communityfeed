package com.seol.communityfeed.acceptance;

import com.seol.communityfeed.acceptance.utils.AcceptanceTestTemplate;
import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.seol.communityfeed.acceptance.steps.FeedAcceptanceSteps.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedAcceptanceTest extends AcceptanceTestTemplate {

    private String token;

    /**
     * User1 - follow -> User2
     * User1 - follow -> User3
     */
    @BeforeEach
    void setUp(){
        super.init();
        this.token = login("user1@gmail.com");
    }

    /**
     * User2 create Post 1
     * User1 Get Post 1 From Feed
     */
    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetPostFromFeed(){
        //given
        CreatePostRequestDto dto = new CreatePostRequestDto(2L, "user 1 can get this test", PostPublicationState.PUBLIC);
        Long createdPostId = requestCreatePost(dto);

        //when, 팔로워 피드를 요청
        List<GetPostContentResponseDto> result = requestFeed(1L);

        //then
        assertEquals(1, result.size());
        assertEquals(createdPostId, result.get(0).getId());

    }

    @Test
    void givenUserHasFollower_whenFollowerUserRequestFeedWithInvalidToken_thenFollowerCanGetPostFromFeed() {
        //given
        //when
        Integer code =requestFeedCode("abcd"); //팔로워 피드 요청

        //then
        assertEquals(400, code);
    }

}
