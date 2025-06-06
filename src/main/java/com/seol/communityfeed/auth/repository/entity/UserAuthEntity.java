package com.seol.communityfeed.auth.repository.entity;

import com.seol.communityfeed.auth.domain.UserAuth;
import com.seol.communityfeed.common.repository.entity.TimeBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_user_auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserAuthEntity extends TimeBaseEntity {

    @Id
    private String email;
    private String password;
    private String role;
    private Long userId;
    private LocalDateTime lastLoginDt;

    public UserAuthEntity(UserAuth userAuth, Long userId){
        this.email = userAuth.getEmail();
        this.password = userAuth.getPassword();
        this.role = userAuth.getUserRole();
        this.userId = userId;
    }

    public UserAuth toUserAuth(){
        return new UserAuth(email, password, role, userId);
    }

    public void updateLastLoginAt(){
        lastLoginDt = LocalDateTime.now();
    }
}
