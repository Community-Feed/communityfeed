package com.seol.communityfeed.auth.ui;

import com.seol.communityfeed.auth.application.AuthService;
import com.seol.communityfeed.auth.application.dto.LoginRequestDto;
import com.seol.communityfeed.auth.application.dto.UserAccessTokenResponseDto;
import com.seol.communityfeed.common.ui.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public Response<UserAccessTokenResponseDto> login(@RequestBody LoginRequestDto dto){
        System.out.println(dto.fcmToken());
        return Response.ok(authService.login(dto));
    }
}
