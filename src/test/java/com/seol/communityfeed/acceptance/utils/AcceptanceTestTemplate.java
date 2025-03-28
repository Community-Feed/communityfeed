package com.seol.communityfeed.acceptance.utils;

import com.seol.communityfeed.auth.application.dto.LoginRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.seol.communityfeed.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AcceptanceTestTemplate {

    @Autowired
    private DatabaseCleanUp cleanUp;

    @Autowired
    private DataLoader loader;

    @BeforeEach
    public void init(){
        RestAssured.port = 8081;
        cleanUp.execute();
        loader.loadDate();
    }

    protected void cleanUp(){
        cleanUp.execute();
    }

    protected String getEmailToken(String email){
        return loader.getEmailToken(email);
    }

    protected boolean isEmailVerified(String email){
        return loader.isEmailVerified(email);
    }

    protected Long getUserId(String email){
        return loader.getUserId(email);
    }

    protected void createUser(String email){
        loader.createUser(email);
    }

    protected String login(String email){
        return requestLoginGetToken(new LoginRequestDto(email, "password"));
    }

}
