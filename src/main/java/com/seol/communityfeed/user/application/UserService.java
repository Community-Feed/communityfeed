package com.seol.communityfeed.user.application;

import com.seol.communityfeed.user.application.Dto.CreateUserRequestDto;
import com.seol.communityfeed.user.application.Dto.GetUserResponseDto;
import com.seol.communityfeed.user.application.Interface.UserRepository;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.domain.UserInfo;
import org.springframework.stereotype.Service;

import java.util.IllformedLocaleException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequestDto dto){
        UserInfo info = new UserInfo(dto.name(), dto.profileImageUrl());
        User user = new User(null, info);
        return userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id);
    }

    public GetUserResponseDto getUserProfile(Long id){
        User user = getUser(id);
        return new GetUserResponseDto(user);
    }

}
