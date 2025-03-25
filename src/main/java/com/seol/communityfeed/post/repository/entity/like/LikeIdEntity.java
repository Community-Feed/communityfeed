package com.seol.communityfeed.post.repository.entity.like;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class LikeIdEntity {
    private Long targetId;
    private Long userId;
    private String targetType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeIdEntity)) return false;
        LikeIdEntity that = (LikeIdEntity) o;
        return Objects.equals(targetId, that.targetId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(targetType, that.targetType); // 대소문자 유의
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetId, userId, targetType);
    }

    @Override
    public String toString() {
        return "LikeIdEntity[targetId=" + targetId +
                ", userId=" + userId +
                ", targetType=" + targetType + "]";
    }
}
