package com.seol.communityfeed.auth.repository;

import com.seol.communityfeed.auth.application.Interface.EmailSendRepository;
import com.seol.communityfeed.auth.domain.Email;
import org.springframework.stereotype.Repository;

@Repository
public class EmailSendRepositoryImpl implements EmailSendRepository {

    @Override
    public void sendEmail(Email email, String randomToken) {

    }
}
