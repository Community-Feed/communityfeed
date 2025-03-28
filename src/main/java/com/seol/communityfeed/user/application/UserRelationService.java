package com.seol.communityfeed.user.application;

import com.seol.communityfeed.user.application.Dto.FollowUserRequestDto;
import com.seol.communityfeed.user.application.Interface.UserRelationRepository;
import com.seol.communityfeed.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserRelationService {
    private final UserService userService;
    private final UserRelationRepository userRelationRepository;

    public UserRelationService(UserService userService, UserRelationRepository userRelationRepository) {
        this.userService = userService;
        this.userRelationRepository = userRelationRepository;
    }

    public void follow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if (user.equals(targetUser)) {
            throw new IllegalStateException("자기 자신을 팔로우할 수 없습니다.");
        }

        if (userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalStateException("이미 팔로우한 사용자입니다.");
        }

        user.follow(targetUser);
        userRelationRepository.save(user, targetUser);
    }

    public void unfollow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if (!userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalStateException("팔로우하지 않은 사용자입니다.");
        }

        user.unfollow(targetUser);
        userRelationRepository.delete(user, targetUser);
    }
}
