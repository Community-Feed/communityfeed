package com.seol.communityfeed.auth.application.Interface;

import com.seol.communityfeed.auth.domain.Email;

public interface EmailSendRepository {
    void sendEmail(Email email, String randomToken);
}
