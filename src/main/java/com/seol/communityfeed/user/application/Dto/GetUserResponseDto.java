package com.seol.communityfeed.user.application.Dto;

import com.seol.communityfeed.user.domain.User;

public record GetUserResponseDto(
        Long id,
        String name,
        String profileImage,
        Integer followingCount,
        Integer followerCount
) {
    public GetUserResponseDto(User user) {
        this(
                user.getId(),
                user.getInfo().getName(),
                user.getInfo().getProfileImageUrl(),
                user.followingCount(),
                user.followerCount()
        );
    }
}
