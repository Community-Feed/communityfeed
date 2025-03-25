package com.seol.communityfeed.user.repository;

import com.seol.communityfeed.post.repository.post_queue.UserPostQueueCommandRepository;
import com.seol.communityfeed.user.application.Interface.UserRelationRepository;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import com.seol.communityfeed.user.repository.entity.UserRelationEntity;
import com.seol.communityfeed.user.repository.entity.UserRelationIdEntity;
import com.seol.communityfeed.user.repository.jpa.JpaUserRelationRepository;
import com.seol.communityfeed.user.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRelationRepositoryImpl implements UserRelationRepository {

    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserRepository jpaUserRepository;
    private final UserPostQueueCommandRepository commandRepository;

    @Override
    public boolean isAlreadyFollow(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());
        return jpaUserRelationRepository.existsById(id);
    }

    @Override
    @Transactional
    public void save(User user, User targetUser) {
        UserRelationEntity entity = new UserRelationEntity(user.getId(), targetUser.getId());
        jpaUserRelationRepository.save(entity);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
        commandRepository.saveFollowPost(user.getId(), targetUser.getId());
    }

    @Override
    @Transactional
    public void delete(User user, User targetUSer) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUSer.getId());
        jpaUserRelationRepository.deleteById(id);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUSer)));
        commandRepository.deleteUnfollowPost(user.getId(), targetUSer.getId());
    }
}
