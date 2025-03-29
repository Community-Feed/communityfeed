package com.seol.communityfeed.post.repository.ui.dto;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostContentResponseDto extends GetContentResponseDto {
    private Integer commentCount; // 게시글에 달린 댓글 수

    public static GetPostContentResponseDto from(PostEntity postEntity) {
        return GetPostContentResponseDto.builder()
                .id(postEntity.getId())
                .content(postEntity.getContent())
                .userId(postEntity.getAuthor().getId())
                .likeCount(postEntity.getLikeCount())
                .createdAt(postEntity.getRegDt())
                .updatedAt(postEntity.getUpdDt())
                .commentCount(postEntity.getCommentCount())
                .build();
    }
}