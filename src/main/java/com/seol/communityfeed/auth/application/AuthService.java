package com.seol.communityfeed.auth.application;

import com.seol.communityfeed.auth.application.Interface.EmailVerificationRepository;
import com.seol.communityfeed.auth.application.Interface.UserAuthRepository;
import com.seol.communityfeed.auth.application.dto.CreateUserAuthRequestDto;
import com.seol.communityfeed.auth.application.dto.LoginRequestDto;
import com.seol.communityfeed.auth.application.dto.UserAccessTokenResponseDto;
import com.seol.communityfeed.auth.domain.Email;
import com.seol.communityfeed.auth.domain.TokenProvider;
import com.seol.communityfeed.auth.domain.UserAuth;
import com.seol.communityfeed.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAuthRepository userAuthRepository;
    private final EmailVerificationRepository verificationRepository;
    private final TokenProvider tokenProvider;

    public Long registerUser(CreateUserAuthRequestDto dto){
        Email email = Email.createEmail(dto.email());

        if(!verificationRepository.isEmailVerified(email)){
            throw new IllegalArgumentException("인증되지 않은 이메일입니다.");
        }

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImageUrl());
        userAuth = userAuthRepository.registerUser(userAuth, user);

        return userAuth.getUserId();
    }

    public UserAccessTokenResponseDto login(LoginRequestDto dto){
        UserAuth userAuth = userAuthRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!userAuth.matchPassword(dto.password())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userAuth = userAuthRepository.loginUser(dto.email(), dto.password(), dto.fcmToken());
        String token = tokenProvider.createToken(userAuth.getUserId(), userAuth.getUserRole());
        return new UserAccessTokenResponseDto(token, userAuth.getUserId()); // ✅ userId 추가된 생성자 사용
    }
}

