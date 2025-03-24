package com.seol.communityfeed.user.repository.jpa;

import com.seol.communityfeed.user.application.Dto.GetUserListResponseDto;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaUserListQueryRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
        SELECT new com.seol.communityfeed.user.application.Dto.GetUserListResponseDto(u.info.name, u.info.profileImageUrl)
        FROM UserRelationEntity ur
        INNER JOIN UserEntity u ON ur.followerUserId = u.id
        WHERE ur.followingUserId = :userId
    """)
    List<GetUserListResponseDto> getFollowingUserList(@Param("userId") Long userId);

    @Query("""
        SELECT new com.seol.communityfeed.user.application.Dto.GetUserListResponseDto(u.info.name, u.info.profileImageUrl)
        FROM UserRelationEntity ur
        INNER JOIN UserEntity u ON ur.followingUserId = u.id
        WHERE ur.followerUserId = :userId
    """)
    List<GetUserListResponseDto> getFollowerUserList(@Param("userId") Long userId);
}
