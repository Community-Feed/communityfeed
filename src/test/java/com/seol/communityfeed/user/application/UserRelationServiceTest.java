package com.seol.communityfeed.user.application;

import com.seol.communityfeed.fake.FakeObjectFactory;
import com.seol.communityfeed.user.application.Dto.CreateUserRequestDto;
import com.seol.communityfeed.user.application.Dto.FollowUserRequestDto;
import com.seol.communityfeed.user.application.Interface.UserRelationRepository;
import com.seol.communityfeed.user.application.Interface.UserRepository;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.FakeUserRelationRepository;
import com.seol.communityfeed.user.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRelationServiceTest {

    private final UserService userService = FakeObjectFactory.getUserService();
    private final UserRelationService userRelationService = FakeObjectFactory.getUserRelationService();

    private User user1;
    private User user2;

    private FollowUserRequestDto requestDto;

    @BeforeEach
    void init() {
        CreateUserRequestDto dto = new CreateUserRequestDto("test", "");
        this.user1 = userService.createUser(dto);
        this.user2 = userService.createUser(dto);

        this.requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());
    }

    @Test
    void givenTwoUsers_whenAFollowB_thenFollowCountChange() {
        // when
        userRelationService.follow(requestDto);

        // then
        assertEquals(1, user1.followingCount());
        assertEquals(1, user2.followerCount());
    }

    @Test
    void givenTwoUserFollowed_whenFollow_thenUserThrowError() {
        // given
        userRelationService.follow(requestDto);

        // when then
        assertThrows(IllegalStateException.class, () -> userRelationService.follow(requestDto));
    }

    @Test
    void givenOneUser_whenFollow_thenUserThrowError() {
        // given
        FollowUserRequestDto dto = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when then
        assertThrows(IllegalStateException.class, () -> userRelationService.follow(dto)); //  올바른 예외 타입으로 수정
    }

    @Test
    void givenTwoUsers_whenAUnfollowB_thenFollowCountChange() {
        // when
        userRelationService.follow(requestDto);
        userRelationService.unfollow(requestDto);

        // then
        assertEquals(0, user1.followingCount());
        assertEquals(0, user2.followerCount());
    }

    @Test
    void givenTwoUser_whenUnfollow_thenUserThrowError() {
        // when then
        assertThrows(IllegalStateException.class, () -> userRelationService.unfollow(requestDto));
    }

    @Test
    void givenOneUser_whenUnfollow_thenUserThrowError() {
        // given
        FollowUserRequestDto dto = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when then
        assertThrows(IllegalStateException.class, () -> userRelationService.unfollow(dto));
    }
}