package com.seol.communityfeed.auth.repository;

import com.seol.communityfeed.auth.application.Interface.UserAuthRepository;
import com.seol.communityfeed.auth.domain.UserAuth;
import com.seol.communityfeed.auth.repository.entity.UserAuthEntity;
import com.seol.communityfeed.auth.repository.jpa.JpaUserAuthRepository;
import com.seol.communityfeed.user.application.Interface.UserRepository;
import com.seol.communityfeed.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JpaUserAuthRepository jpaUserAuthRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserAuth registerUser(UserAuth auth, User user) {
        User savedUser = userRepository.save(user);
        UserAuthEntity userAuthEntity = new UserAuthEntity(auth, savedUser.getId());
        userAuthEntity = jpaUserAuthRepository.save(userAuthEntity);
        return userAuthEntity.toUserAuth();
    }
}
