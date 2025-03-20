package com.seol.communityfeed.post.application.Dto;

public record UpdateCommentRequestDto(Long commentId, Long userId, String content) {
}
