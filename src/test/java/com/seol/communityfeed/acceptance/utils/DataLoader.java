package com.seol.communityfeed.acceptance.utils;

import com.seol.communityfeed.acceptance.steps.UserAcceptanceSteps;
import com.seol.communityfeed.user.application.Dto.CreateUserRequestDto;
import com.seol.communityfeed.user.application.Dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

import static com.seol.communityfeed.acceptance.steps.UserAcceptanceSteps.createUser;
import static com.seol.communityfeed.acceptance.steps.UserAcceptanceSteps.followUser;

@Component
public class DataLoader {

    public void loadDate(){
        CreateUserRequestDto dto = new CreateUserRequestDto("test user", "");
        UserAcceptanceSteps.createUser(dto);
        UserAcceptanceSteps.createUser(dto);
        UserAcceptanceSteps.createUser(dto);

        UserAcceptanceSteps.followUser(new FollowUserRequestDto(1L, 2L));
        UserAcceptanceSteps.followUser(new FollowUserRequestDto(1L, 3L));
    }
}