package com.seol.communityfeed.post.repository.entity.like;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LikeIdEntity {
    private Long targetId;
    private Long userId;
    private String targetType;
}
