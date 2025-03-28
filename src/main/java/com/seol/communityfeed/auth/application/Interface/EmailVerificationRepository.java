package com.seol.communityfeed.auth.application.Interface;

import com.seol.communityfeed.auth.domain.Email;

public interface EmailVerificationRepository {
    void createEmailVerification(Email email, String token);
    void verifyEmail(Email email, String token);
    boolean isEmailVerified(Email email);
}
