package com.seol.communityfeed.auth.ui;

import com.seol.communityfeed.auth.application.AuthService;
import com.seol.communityfeed.auth.application.EmailService;
import com.seol.communityfeed.auth.application.dto.CreateUserAuthRequestDto;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import com.seol.communityfeed.common.ui.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final EmailService emailService;
    private final AuthService authService;

    @PostMapping("/send-verification-email")
    public Response<Void> sendEmail(@RequestBody SendEmailRequestDto dto){
        emailService.SendEmail(dto);
        return Response.ok(null);
    }

    @GetMapping("/verify-token")
    public Response<Void> verifyEmail(String email, String token){
        emailService.verifyEmail(email, token);
        return Response.ok(null);
    }

    @PostMapping("/register")
    public Response<Long> register(@RequestBody CreateUserAuthRequestDto dto){
        return Response.ok(authService.registerUser(dto));
    }
}
