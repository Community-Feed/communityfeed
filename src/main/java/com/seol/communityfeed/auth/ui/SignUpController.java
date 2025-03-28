package com.seol.communityfeed.auth.ui;

import com.seol.communityfeed.auth.application.EmailService;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import com.seol.communityfeed.common.ui.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final EmailService emailService;

    @PostMapping("/send-verification-email")
    public Response<Void> sendEmail(@RequestBody SendEmailRequestDto dto){
        emailService.SendEmail(dto);
        return Response.ok(null);
    }
}
