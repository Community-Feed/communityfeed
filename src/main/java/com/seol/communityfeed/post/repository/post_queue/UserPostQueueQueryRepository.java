package com.seol.communityfeed.post.repository.post_queue;

import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;

import java.util.List;

public interface UserPostQueueQueryRepository {
    List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastPostId);
}
