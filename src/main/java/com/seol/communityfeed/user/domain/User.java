package com.seol.communityfeed.user.domain;

import java.util.Objects;

public class User {

    private final Long id;
    private final UserInfo info;

    public User(Long id, UserInfo userInfo){
        this.id = id;
        this.info = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
