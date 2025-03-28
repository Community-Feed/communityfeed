package com.seol.communityfeed.auth.application;

import com.seol.communityfeed.auth.application.Interface.EmailVerificationRepository;
import com.seol.communityfeed.auth.application.Interface.UserAuthRepository;
import com.seol.communityfeed.auth.application.dto.CreateUserAuthRequestDto;
import com.seol.communityfeed.auth.domain.Email;
import com.seol.communityfeed.auth.domain.UserAuth;
import com.seol.communityfeed.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAuthRepository userAuthRepository;
    private final EmailVerificationRepository verificationRepository;

    public Long registerUser(CreateUserAuthRequestDto dto){
        Email email = Email.createEmail(dto.email());

        if(!verificationRepository.isEmailVerified(email)){
            throw  new IllegalArgumentException("인증되지 않은 이메일입니다.");
        }

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImageUrl());
        userAuth = userAuthRepository.registerUser(userAuth, user);

        return userAuth.getUserId();
    }
}
