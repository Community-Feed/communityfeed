package com.seol.communityfeed.auth.application;

import com.seol.communityfeed.auth.application.Interface.EmailSendRepository;
import com.seol.communityfeed.auth.application.Interface.EmailVerificationRepository;
import com.seol.communityfeed.auth.application.dto.SendEmailRequestDto;
import com.seol.communityfeed.auth.domain.Email;
import com.seol.communityfeed.auth.domain.RandomTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailSendRepository emailSendRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    public void SendEmail(SendEmailRequestDto dto){
        Email email = Email.createEmail(dto.email());
        String token = RandomTokenGenerator.generateToken();

        emailSendRepository.sendEmail(email, token);
        emailVerificationRepository.createEmailVerification(email, token);
    }
}
