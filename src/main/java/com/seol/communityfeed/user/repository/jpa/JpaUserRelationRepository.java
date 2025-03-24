package com.seol.communityfeed.user.repository.jpa;

import com.seol.communityfeed.user.repository.entity.UserRelationEntity;
import com.seol.communityfeed.user.repository.entity.UserRelationIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationIdEntity> {
}
