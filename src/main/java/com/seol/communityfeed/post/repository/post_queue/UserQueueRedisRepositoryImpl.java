package com.seol.communityfeed.post.repository.post_queue;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueueRedisRepositoryImpl implements UserQueueRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publishPostToFollowingUserList(PostEntity postEntity, List<Long> userIdList) {
        for (Long userId : userIdList) {
            String key = "feed:" + userId;
            redisTemplate.opsForList().leftPush(key, postEntity.getId());
        }
    }

    @Override
    public void publishPostListToFollowerUser(List<PostEntity> postEntityList, Long userId) {
        String key = "feed:" + userId;
        for (PostEntity post : postEntityList) {
            redisTemplate.opsForList().leftPush(key, post.getId());
        }
    }

    @Override
    public void deleteDeleteFeed(Long userId, Long authorId) {
        String key = "feed:" + userId;
        redisTemplate.delete(key);
    }
}
