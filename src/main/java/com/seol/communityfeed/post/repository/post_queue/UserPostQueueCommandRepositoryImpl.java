package com.seol.communityfeed.post.repository.post_queue;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.entity.post.UserPostQueueEntity;
import com.seol.communityfeed.post.repository.jpa.JpaPostRepository;
import com.seol.communityfeed.post.repository.jpa.JpaUserPostQueueRepository;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import com.seol.communityfeed.user.repository.jpa.JpaUserRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seol.communityfeed.post.repository.entity.post.QPostEntity.postEntity;
import static com.seol.communityfeed.user.repository.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class UserPostQueueCommandRepositoryImpl implements UserPostQueueCommandRepository{

    private final JpaPostRepository jpaPostRepository;
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserPostQueueRepository jpaUserPostQueueRepository;

    @Override
    @Transactional
    public void publishPost(PostEntity postEntity) {
        UserEntity userEntity = postEntity.getAuthor();
        List<Long> followerIds = jpaUserRelationRepository.findFollowers(userEntity.getId());

        List<UserPostQueueEntity> userPostQueueEntityList = followerIds.stream()
                .map(userId ->new UserPostQueueEntity(userId, postEntity.getId(), userEntity.getId()))
                .toList();

        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);
    }

    @Override
    @Transactional
    public void saveFollowPost(Long userId, Long targetId) {
        List<Long> postIdList = jpaPostRepository.findAllPostIdsByAuthorId(targetId);

        List<UserPostQueueEntity> userPostQueueEntityList = postIdList.stream()
                .map(postId ->new UserPostQueueEntity(userId, postId, targetId))
                .toList();
        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);

    }

    @Override
    @Transactional
    public void deleteUnfollowPost(Long userId, Long targetId) {
        jpaUserPostQueueRepository.deleteAllByUserIdAndAuthorId(userId, targetId);
    }
}
