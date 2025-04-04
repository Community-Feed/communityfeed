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

import java.util.Optional;

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

    @Override
    public UserAuth loginUser(String emaill, String password) {
        UserAuthEntity userAuthEntity = jpaUserAuthRepository.findById(emaill).orElseThrow();
        UserAuth userAuth = userAuthEntity.toUserAuth();

        if(!userAuth.matchPassword(password)){
            throw new IllegalArgumentException("옳지 않은 비밀번호 입니다.");
        }

        return userAuth;
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return jpaUserAuthRepository.findById(email)
                .map(UserAuthEntity::toUserAuth);
    }
}
