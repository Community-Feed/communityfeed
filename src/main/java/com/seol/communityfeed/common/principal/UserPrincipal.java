package com.seol.communityfeed.common.principal;

import com.seol.communityfeed.auth.domain.UserRole;
import lombok.Getter;

@Getter
public class UserPrincipal {

    private Long userId;
    private UserRole userRole;

    public UserPrincipal(Long userId, String role){
        this.userId = userId;
        this.userRole = UserRole.valueOf(role);
    }
}
