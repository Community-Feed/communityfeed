package com.seol.communityfeed.user.repository.jpa;

import com.seol.communityfeed.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
}
