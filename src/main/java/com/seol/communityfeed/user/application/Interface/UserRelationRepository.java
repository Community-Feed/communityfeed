package com.seol.communityfeed.user.application.Interface;

import com.seol.communityfeed.user.domain.User;

public interface UserRelationRepository {
    boolean isAlreadyFollow(User user, User targetUser);
    void save(User user, User targetUser);
    void delete(User user, User targetUSer);
}
