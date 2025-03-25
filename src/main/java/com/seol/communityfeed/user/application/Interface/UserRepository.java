package com.seol.communityfeed.user.application.Interface;

import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
}