package com.seol.communityfeed.post.repository.post_queue;

import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Qualifier("userPostQueueQueryRepositoryImpl")
public interface UserPostQueueQueryRepository {
    List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastPostId);
}
