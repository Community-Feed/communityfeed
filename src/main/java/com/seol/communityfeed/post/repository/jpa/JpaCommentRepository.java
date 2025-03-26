package com.seol.communityfeed.post.repository.jpa;

import com.seol.communityfeed.post.repository.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE CommentEntity c SET c.content = :content, c.updDt = CURRENT_TIMESTAMP WHERE c.id = :id")
    int updateComment(@Param("id") Long id, @Param("content") String content);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE community_comment " +
            "SET like_count = GREATEST(like_count + :likeCount, 0), " +
            "upd_dt = CURRENT_TIMESTAMP " +
            "WHERE id = :commentId", nativeQuery = true)
    int updateLikeCount(@Param("commentId") Long commentId, @Param("likeCount") Integer likeCount);

}

