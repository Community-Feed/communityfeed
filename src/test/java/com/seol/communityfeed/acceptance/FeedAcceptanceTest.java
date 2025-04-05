package com.seol.communityfeed.acceptance;

import com.seol.communityfeed.acceptance.steps.FeedAcceptanceSteps;
import com.seol.communityfeed.acceptance.utils.AcceptanceTestTemplate;
import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FeedAcceptanceTest extends AcceptanceTestTemplate {

    private String user1Token;
    private String user2Token;
    private Long user2Id;

    @BeforeEach
    void setUp() {
        super.init();

        FeedAcceptanceSteps.LoginInfo user1 = FeedAcceptanceSteps.registerAndGetLoginInfo("user1@gmail.com", "password");
        FeedAcceptanceSteps.LoginInfo user2 = FeedAcceptanceSteps.registerAndGetLoginInfo("user2@gmail.com", "password");

        user1Token = user1.accessToken;
        user2Token = user2.accessToken;
        user2Id = user2.userId;

        FeedAcceptanceSteps.requestFollow(user2Id, user1Token);
    }

    /*
    @Test
    void givenUserHasFollowerAndCreatePost_whenFollowerUserRequestFeed_thenFollowerCanGetPostFromFeed() {
        // given: User2가 게시글 생성
        CreatePostRequestDto dto = new CreatePostRequestDto(user2Id, "user 1 can get this test", PostPublicationState.PUBLIC);
        Long createdPostId = FeedAcceptanceSteps.requestCreatePost(dto, user2Token);

        // when: User1이 피드 조회
        List<GetPostContentResponseDto> result = FeedAcceptanceSteps.requestFeed(user1Token);

        // then
        assertEquals(1, result.size());
        assertEquals(createdPostId, result.get(0).getId());
    }

    @Test
    void givenUserHasFollower_whenFollowerUserRequestFeedWithInvalidToken_thenFollowerCanGetPostFromFeed() {
        // when
        Integer code = FeedAcceptanceSteps.requestFeedCode("abcd"); // 유효하지 않은 토큰으로 피드 조회

        // then
        assertEquals(400, code);
    }*/
}
