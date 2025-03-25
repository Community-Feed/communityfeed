package com.seol.communityfeed.user.repository;

import com.seol.communityfeed.user.application.Interface.UserRepository;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import com.seol.communityfeed.user.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user);
        return jpaUserRepository.save(entity).toUser();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(UserEntity::toUser);
    }
}