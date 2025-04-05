package com.seol.communityfeed.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    private String name;
    private String profileImageUrl;

    public UserInfo(String name, String profileImageUrl){
        if(name == null || name.isEmpty()){
            throw new IllegalStateException("이름은 비어있을 수 없습니다.");
        }

        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}
