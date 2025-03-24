package com.seol.communityfeed.user.domain;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    private Long id;

    @Embedded
    private UserInfo info;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "count", column = @Column(name = "following_count")),
            @AttributeOverride(name = "maxLimit", column = @Column(name = "following_max_limit"))
    })
    private PositiveIntegerCounter followingCount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "count", column = @Column(name = "follower_count")),
            @AttributeOverride(name = "maxLimit", column = @Column(name = "follower_max_limit"))
    })
    private PositiveIntegerCounter followerCounter;

    public User(Long id, UserInfo userInfo) {
        if (userInfo == null) {
            throw new IllegalArgumentException("UserInfo must not be null");
        }

        this.id = id;
        this.info = userInfo;
        this.followingCount = new PositiveIntegerCounter();
        this.followerCounter = new PositiveIntegerCounter();
    }

    public void follow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new IllegalArgumentException("You cannot follow yourself.");
        }

        followingCount.increase();
        targetUser.increaseFollowerCount();
    }

    public void unfollow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new IllegalArgumentException("You cannot unfollow yourself.");
        }

        followingCount.decrease();
        targetUser.decreaseFollowerCount();
    }

    private void increaseFollowerCount() {
        followerCounter.increase();
    }

    private void decreaseFollowerCount() {
        followerCounter.decrease();
    }

    public Long getId() {
        return id;
    }

    public int followerCount() {
        return followerCounter.getCount();
    }

    public int followingCount() {
        return followingCount.getCount();
    }

    public UserInfo getInfo() {
        return info;
    }

    public PositiveIntegerCounter getFollowingCount() {
        return followingCount;
    }

    public PositiveIntegerCounter getFollowerCounter() {
        return followerCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
