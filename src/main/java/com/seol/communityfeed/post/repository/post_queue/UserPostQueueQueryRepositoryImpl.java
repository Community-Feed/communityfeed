package com.seol.communityfeed.post.repository.post_queue;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.jpa.JpaPostRepository;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Primary
public class UserPostQueueQueryRepositoryImpl implements UserPostQueueQueryRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final JpaPostRepository jpaPostRepository;

    @Override
    public List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastPostId) {
        // Redis에서 게시물 ID 리스트를 가져온다고 가정 (key: "feed:{userId}")
        String redisKey = "feed:" + userId;

        List<String> postIdStrings = redisTemplate.opsForList().range(redisKey, 0, -1);
        if (postIdStrings == null || postIdStrings.isEmpty()) {
            return Collections.emptyList();
        }

        // Long으로 파싱하고, lastPostId 이후의 게시물만 가져오게 필터링
        List<Long> postIds = postIdStrings.stream()
                .map(Long::parseLong)
                .filter(id -> lastPostId == null || id > lastPostId)
                .collect(Collectors.toList());

        // DB에서 해당 postId에 해당하는 PostEntity를 가져옴
        List<PostEntity> postEntities = jpaPostRepository.findAllById(postIds);

        // PostEntity를 DTO로 매핑
        return postEntities.stream()
                .map(GetPostContentResponseDto::from)
                .collect(Collectors.toList());
    }
}
