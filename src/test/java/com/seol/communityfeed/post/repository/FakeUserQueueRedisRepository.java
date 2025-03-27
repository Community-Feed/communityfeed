package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.post_queue.UserPostQueueCommandRepositoryImpl;
import com.seol.communityfeed.post.repository.post_queue.UserQueueRedisRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("test")
public class FakeUserQueueRedisRepository implements UserQueueRedisRepository {

    private final UserPostQueueCommandRepositoryImpl userPostQueueCommandRepositoryImpl;

    // 테스트용 큐: 유저 ID별로 게시글 Set 저장
    private final Map<Long, Set<PostEntity>> queue = new HashMap<>();

    public FakeUserQueueRedisRepository(@Lazy UserPostQueueCommandRepositoryImpl userPostQueueCommandRepositoryImpl) {
        this.userPostQueueCommandRepositoryImpl = userPostQueueCommandRepositoryImpl;
    }

    @Override
    public void publishPostToFollowingUserList(PostEntity postEntity, List<Long> userIdList) {
        for (Long userId : userIdList) {
            queue.computeIfAbsent(userId, k -> new HashSet<>()).add(postEntity);
        }
    }

    @Override
    public void publishPostListToFollowerUser(List<PostEntity> postEntityList, Long userId) {
        queue.computeIfAbsent(userId, k -> new HashSet<>()).addAll(postEntityList);
    }

    @Override
    public void deleteDeleteFeed(Long userId, Long authorId) {
        if (queue.containsKey(userId)) {
            queue.get(userId).removeIf(post -> post.getAuthor().getId().equals(authorId));
        }
    }

    public List<PostEntity> getPostListByUserId(Long userId){
        return List.copyOf(queue.get(userId));
    }

    // 테스트용 getter (선택)
    public Set<PostEntity> getPostsByUser(Long userId) {
        return queue.getOrDefault(userId, Collections.emptySet());
    }
}
