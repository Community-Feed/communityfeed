package com.seol.communityfeed.auth.application.Interface;

import com.seol.communityfeed.auth.domain.UserAuth;
import com.seol.communityfeed.user.domain.User;

import java.util.Optional;

public interface UserAuthRepository {

    UserAuth registerUser(UserAuth userAuth, User user);
    UserAuth loginUser(String emaill, String password);
    Optional<UserAuth> findByEmail(String email);

}
