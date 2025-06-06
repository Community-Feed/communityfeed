package com.seol.communityfeed.user.application.Interface;

import com.seol.communityfeed.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
}