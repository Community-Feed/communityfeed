package com.seol.communityfeed.post.repository.jpa;

import com.seol.communityfeed.post.repository.entity.comment.CommentEntity;
import com.seol.communityfeed.post.repository.entity.post.PostEntity;
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
    @Query("UPDATE CommentEntity c SET c.likeCount = c.likeCount +:likeCount, c.updDt = CURRENT_TIMESTAMP WHERE c.id = :commentId")
    int updateLikeCount(Long commentId, Integer likeCount);
}

