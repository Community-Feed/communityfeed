package com.seol.communityfeed.auth.repository;

import com.seol.communityfeed.auth.application.Interface.EmailVerificationRepository;
import com.seol.communityfeed.auth.domain.Email;
import com.seol.communityfeed.auth.repository.entity.EmailVerificationEntity;
import com.seol.communityfeed.auth.repository.jpa.JpaEmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRepository jpaEmailVerificationRepository;

    @Override
    public void createEmailVerification(Email email, String token) {
        String emailAddress = email.getEmailText();
        Optional<EmailVerificationEntity> entity = jpaEmailVerificationRepository.findByEmail(emailAddress);

        if (entity.isPresent()) {
            EmailVerificationEntity emailVerificationEntity = entity.get();

            if (emailVerificationEntity.isVerified()) {
                throw new IllegalArgumentException("이미 인증된 이메일입니다.");
            }

            emailVerificationEntity.updateToken(token); // ✅ 여기서 token 갱신
            jpaEmailVerificationRepository.save(emailVerificationEntity); // ✅ 변경 사항 저장
            return;
        }

        EmailVerificationEntity emailVerificationEntity = new EmailVerificationEntity(emailAddress, token);
        jpaEmailVerificationRepository.save(emailVerificationEntity); // ✅ 새로 생성해서 저장
    }

}
