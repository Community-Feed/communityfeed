package com.seol.communityfeed.user.repository;

import com.seol.communityfeed.user.application.Interface.UserRepository;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import com.seol.communityfeed.user.repository.jpa.JpaUserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user);
        entity = jpaUserRepository.save(entity);
        return entity.toUser();
    }



    @Override
    public User findById(Long id) {
        UserEntity userEntity = jpaUserRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return userEntity.toUser();
    }
}