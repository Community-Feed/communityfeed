package com.seol.communityfeed.auth.repository.entity;

import com.seol.communityfeed.common.repository.entity.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="community_email_verification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailVerificationEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;
    private boolean isVerified;

    public EmailVerificationEntity(String email, String token){
        this.email = email;
        this.token = token;
        this.isVerified = false;
    }

    public boolean isVerified(){
        return isVerified;
    }

    public void updateToken(String token){
        this.token = token;
    }

    public void verify(){
        this.isVerified = true;
    }

    public boolean hasSameToken(String token){
        return this.token.equals(token);
    }
}
