package com.seol.communityfeed.post.repository.post_queue;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.jpa.JpaPostRepository;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import com.seol.communityfeed.user.repository.jpa.JpaUserRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPostQueueCommandRepositoryImpl implements UserPostQueueCommandRepository{

    private final JpaPostRepository jpaPostRepository;
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final UserQueueRedisRepository redisRepository;

    @Override
    @Transactional
    public void publishPost(PostEntity postEntity) {
        UserEntity userEntity = postEntity.getAuthor();
        List<Long> followerIds = jpaUserRelationRepository.findFollowers(userEntity.getId());
        redisRepository.publishPostToFollowingUserList(postEntity, followerIds);

    }

    @Override
    @Transactional
    public void saveFollowPost(Long userId, Long targetId) {
        List<PostEntity> postEntities = jpaPostRepository.findAllPostIdsByAuthorId(targetId);
        redisRepository.publishPostListToFollowerUser(postEntities, userId);
    }

    @Override
    @Transactional
    public void deleteUnfollowPost(Long userId, Long targetId) {
        redisRepository.deleteDeleteFeed(userId, targetId);
    }
}
