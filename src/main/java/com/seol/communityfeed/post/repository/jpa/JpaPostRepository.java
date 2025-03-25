package com.seol.communityfeed.post.repository.jpa;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PostEntity p " +
            "SET p.content = :#{#postEntity.content}, " +
            "p.state = :#{#postEntity.state}, " +
            "p.updDt = CURRENT_TIMESTAMP " +
            "WHERE p.id = :#{#postEntity.id}")
    void updatePostEntity(@Param("postEntity") PostEntity postEntity);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PostEntity p " +
            "SET p.likeCount = :#{#postEntity.likeCount}, " +
            "p.updDt = CURRENT_TIMESTAMP " +
            "WHERE p.id = :#{#postEntity.id}")
    void updateLikeCount(@Param("postEntity") PostEntity postEntity);
}