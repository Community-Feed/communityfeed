package com.seol.communityfeed.user.repository.entity;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.common.repository.entity.TimeBaseEntity;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.domain.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Table(name = "community_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate //변경된 값들만 업데이트
public class UserEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // ✅ User -> UserEntity 변환용 생성자
    public UserEntity(User user) {
        if (user == null || user.getInfo() == null) {
            throw new IllegalArgumentException("User 또는 UserInfo는 null일 수 없습니다.");
        }
        this.id = user.getId();
        this.info = user.getInfo();
        this.followingCount = user.getFollowingCount();
        this.followerCounter = user.getFollowerCounter();
    }

    // ✅ UserEntity -> User 변환 메서드
    public User toUser() {
        return User.builder()
                .id(this.id)
                .info(this.info)
                .followingCount(this.followingCount)
                .followerCounter(this.followerCounter)
                .build();
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

    public User toDomain() {
        return User.builder()
                .id(this.id)
                .info(this.info)
                .followingCount(this.followingCount)
                .followerCounter(this.followerCounter)
                .build();
    }
}
