package com.seol.communityfeed.acceptance.utils;

import com.seol.communityfeed.acceptance.steps.UserAcceptanceSteps;
import com.seol.communityfeed.user.application.Dto.CreateUserRequestDto;
import com.seol.communityfeed.user.application.Dto.FollowUserRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    public void loadDate(){
        CreateUserRequestDto dto = new CreateUserRequestDto("test user", "");
        UserAcceptanceSteps.createUser(dto);
        UserAcceptanceSteps.createUser(dto);
        UserAcceptanceSteps.createUser(dto);

        UserAcceptanceSteps.followUser(new FollowUserRequestDto(1L, 2L));
        UserAcceptanceSteps.followUser(new FollowUserRequestDto(1L, 3L));
    }

    public String getEmailToken(String email){
        return entityManager.createNativeQuery("SELECT token FROM community_email_verification WHERE email =?", String.class)
                .setParameter(1,email)
                .getSingleResult()
                .toString();
    }
}