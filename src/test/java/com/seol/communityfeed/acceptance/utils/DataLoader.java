package com.seol.communityfeed.acceptance.utils;

import com.seol.communityfeed.acceptance.steps.UserAcceptanceSteps;
import com.seol.communityfeed.auth.application.dto.CreateUserAuthRequestDto;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import com.seol.communityfeed.user.application.Dto.FollowUserRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import static com.seol.communityfeed.acceptance.steps.SignUpAcceptanceSteps.*;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    public void loadDate(){
        // user 1, 2, 3 생성
        for(int i=1; i<4; i++){
            createUser("user"+i+"@test.com");
        }

        UserAcceptanceSteps.followUser(new FollowUserRequestDto(1L, 2L));
        UserAcceptanceSteps.followUser(new FollowUserRequestDto(1L, 3L));
    }

    public String getEmailToken(String email){
        return entityManager.createNativeQuery("SELECT token FROM community_email_verification WHERE email =?", String.class)
                .setParameter(1,email)
                .getSingleResult()
                .toString();
    }

    public boolean isEmailVerified(String email){
        return entityManager.createQuery("SELECT isVerified FROM EmailVerificationEntity WHERE email = :email ", Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Long getUserId(String email){
        return entityManager.createQuery("SELECT userId FROM UserAuthEntity WHERE email =:email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public void createUser(String email){
        requestSendEmail(new SendEmailRequestDto(email));
        String token = getEmailToken(email);
        requestVerifyEmail(email, token);
        registerUser(new CreateUserAuthRequestDto(email,"password","USER","name",""));
    }
}