package com.seol.communityfeed.user.domain;

public class UserInfo {

    private final String name;
    private final String profileImageUrl;

    public UserInfo(String name, String profileImageUrl){

        if(name == null || name.isEmpty()){
            throw new IllegalStateException("이름은 비어있을 수 없습니다.");
        }

        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
