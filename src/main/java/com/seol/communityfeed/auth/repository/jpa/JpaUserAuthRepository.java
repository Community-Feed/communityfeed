package com.seol.communityfeed.auth.repository.jpa;

import com.seol.communityfeed.auth.domain.UserAuth;
import com.seol.communityfeed.auth.repository.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserAuthRepository extends JpaRepository<UserAuthEntity, String> {
}
