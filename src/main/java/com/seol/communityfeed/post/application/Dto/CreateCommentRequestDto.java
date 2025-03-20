package com.seol.communityfeed.post.application.Dto;

public record CreateCommentRequestDto(Long postId, Long userId, String content) {
}
