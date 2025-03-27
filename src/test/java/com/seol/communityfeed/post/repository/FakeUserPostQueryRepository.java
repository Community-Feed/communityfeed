package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.post_queue.UserPostQueueQueryRepository;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("test")
public class FakeUserPostQueryRepository implements UserPostQueueQueryRepository {

    private final FakeUserQueueRedisRepository fakeUserQueueRedisRepository;

    public FakeUserPostQueryRepository(FakeUserQueueRedisRepository fakeUserQueueRedisRepository) {
        this.fakeUserQueueRedisRepository = fakeUserQueueRedisRepository;
    }

    @Override
    public List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastPostId) {
        List<PostEntity> postEntities = fakeUserQueueRedisRepository.getPostListByUserId(userId);

        return postEntities.stream()
                .filter(post -> lastPostId == null || post.getId() < lastPostId)
                .map(post -> GetPostContentResponseDto.builder()
                        .id(post.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
