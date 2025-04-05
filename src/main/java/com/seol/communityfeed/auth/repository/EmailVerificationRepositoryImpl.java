package com.seol.communityfeed.auth.repository;

import com.seol.communityfeed.auth.application.Interface.EmailVerificationRepository;
import com.seol.communityfeed.auth.domain.Email;
import com.seol.communityfeed.auth.repository.entity.EmailVerificationEntity;
import com.seol.communityfeed.auth.repository.jpa.JpaEmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRepository jpaEmailVerificationRepository;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void verifyEmail(Email email, String token) {
        String emailAddress = email.getEmailText();

        EmailVerificationEntity entity = jpaEmailVerificationRepository.findByEmail(emailAddress)
                .orElseThrow(()->new IllegalArgumentException("인증 요청하지 않은 이메일입니다."));

        if(entity.isVerified()){
            throw new IllegalArgumentException("이미 인증된 이메일입니다.");
        }

        if(!entity.hasSameToken(token)){
            throw new IllegalArgumentException("토큰 값이 유효하지 않습니다.");
        }

        entity.verify();
    }

    @Override
    public boolean isEmailVerified(Email email){
        EmailVerificationEntity entity =  jpaEmailVerificationRepository.findByEmail(email.getEmailText())
                .orElseThrow(()->new IllegalArgumentException("인증 요청하지 않은 이메일입니다."));
        return entity.isVerified();
    }

}
