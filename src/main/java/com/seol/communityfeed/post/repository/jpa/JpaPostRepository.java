package com.seol.communityfeed.post.repository.jpa;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE p.author.id = :authorId")
    List<PostEntity> findAllPostIdsByAuthorId(Long authorId);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PostEntity p " +
            "SET p.content = :#{#postEntity.content}, " +
            "p.state = :#{#postEntity.state}, " +
            "p.updDt = CURRENT_TIMESTAMP " +
            "WHERE p.id = :#{#postEntity.getId()}")
    void updatePostEntity(@Param("postEntity") PostEntity postEntity);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PostEntity p " +
            "SET p.commentCount = p.commentCount + 1, " +
            "p.updDt = CURRENT_TIMESTAMP " +
            "WHERE p.id = :id")
    void increaseCommentCount(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE community_post SET like_count = GREATEST(like_count + :likeCount, 0), upd_dt = CURRENT_TIMESTAMP WHERE id = :postId", nativeQuery = true)
    void updateLikeCount(@Param("postId") Long postId, @Param("likeCount") Integer likeCount);

}