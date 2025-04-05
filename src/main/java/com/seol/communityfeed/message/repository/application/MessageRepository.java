package com.seol.communityfeed.message.repository.application;

import com.seol.communityfeed.user.domain.User;

public interface MessageRepository {

    void sendLikeMessage(User user, User targetUser);
}
