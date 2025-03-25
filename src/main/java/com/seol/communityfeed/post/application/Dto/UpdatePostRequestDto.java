package com.seol.communityfeed.post.application.Dto;

import com.seol.communityfeed.post.domain.content.PostPublicationState;

public record UpdatePostRequestDto(Long userId, String content, PostPublicationState state) {
}
