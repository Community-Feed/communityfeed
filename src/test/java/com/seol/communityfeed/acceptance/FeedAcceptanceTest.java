package com.seol.communityfeed.acceptance;

import com.seol.communityfeed.acceptance.steps.FeedAcceptanceSteps;
import com.seol.communityfeed.acceptance.steps.SignUpAcceptanceSteps;
import com.seol.communityfeed.acceptance.utils.AcceptanceTestTemplate;
import com.seol.communityfeed.auth.application.dto.CreateUserAuthRequestDto;
import com.seol.communityfeed.auth.application.dto.LoginRequestDto;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.seol.communityfeed.acceptance.steps.FeedAcceptanceSteps.*;
import static com.seol.communityfeed.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedAcceptanceTest extends AcceptanceTestTemplate {

    private String user1Token;
    private String user2Token;
    private Long user2Id = 2L;

    @BeforeEach
    void setUp() {
        super.init();

        // User1 생성
        user1Token = createAndLoginUser("user1@gmail.com", "nickname1");

        // User2 생성
        user2Token = createAndLoginUser("user2@gmail.com", "nickname2");

        // User1 → User2 팔로우
        FeedAcceptanceSteps.requestFollow(user2Id, user1Token);
    }

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
    void givenUserHasFollower_whenFollowerUserRequestFeedWithInvalidToken_thenFailWith400() {
        // when
        Integer code = FeedAcceptanceSteps.requestFeedCode("abcd");

        // then
        assertEquals(400, code);
    }

    // ✅ 유저 생성 및 로그인 공통 로직
    private String createAndLoginUser(String email, String nickname) {
        // 1. 인증 이메일 요청
        String verificationToken = SignUpAcceptanceSteps.requestSendEmailAndGetToken(new SendEmailRequestDto(email));

        // 2. 이메일 인증
        SignUpAcceptanceSteps.requestVerifyEmail(email, verificationToken);

        // 3. 회원가입
        SignUpAcceptanceSteps.registerUser(new CreateUserAuthRequestDto(
                email, "password", "USER", nickname, "https://example.com/img"
        ));

        // 4. 로그인 후 토큰 발급
        return requestLoginGetToken(new LoginRequestDto(email, "password"));
    }
}
