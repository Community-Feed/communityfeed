package com.seol.communityfeed.message.repository;

import com.seol.communityfeed.message.repository.entity.FcmTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {
}
