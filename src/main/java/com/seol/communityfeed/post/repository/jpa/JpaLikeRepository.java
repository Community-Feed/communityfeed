package com.seol.communityfeed.post.repository.jpa;

import com.seol.communityfeed.post.repository.entity.like.LikeEntity;
import com.seol.communityfeed.post.repository.entity.like.LikeIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, LikeIdEntity> {
}
