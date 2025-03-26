package com.seol.communityfeed.post.repository.ui.dto;

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
}