package com.seol.communityfeed.user.repository.entity;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.common.repository.entity.TimeBaseEntity;
import com.seol.communityfeed.user.domain.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "community_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity extends TimeBaseEntity {

    @Id
    private Long id;

    @Embedded
    private UserInfo info;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "count", column = @Column(name = "following_count"))
    })
    private PositiveIntegerCounter followingCount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "count", column = @Column(name = "follower_count"))
    })
    private PositiveIntegerCounter followerCounter;

    public UserEntity(Long id, UserInfo info) {
        if (info == null) throw new IllegalArgumentException("UserInfo는 null일 수 없습니다.");
        this.id = id;
        this.info = info;
        this.followingCount = new PositiveIntegerCounter();
        this.followerCounter = new PositiveIntegerCounter();
    }

    public void follow(UserEntity targetUser) {
        if (this.equals(targetUser)) throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        this.followingCount.increase();
        targetUser.increaseFollowerCount();
    }

    public void unfollow(UserEntity targetUser) {
        if (this.equals(targetUser)) throw new IllegalArgumentException("자기 자신을 언팔로우할 수 없습니다.");
        this.followingCount.decrease();
        targetUser.decreaseFollowerCount();
    }

    private void increaseFollowerCount() {
        this.followerCounter.increase();
    }

    private void decreaseFollowerCount() {
        this.followerCounter.decrease();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
