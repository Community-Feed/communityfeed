package com.seol.communityfeed.post.application.Dto;

import com.seol.communityfeed.post.domain.content.PostPublicationState;

public record CreatePostRequestDto(Long userId, String content, PostPublicationState state) {

}
