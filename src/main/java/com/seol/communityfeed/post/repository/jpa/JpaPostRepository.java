package com.seol.communityfeed.post.repository.jpa;

import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p.id FROM PostEntity p WHERE p.author.id = :authorId")
    List<Long> findAllPostIdsByAuthorId(Long authorId);


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
            "SET p.likeCount = p.likeCount + :likeCount, " +
            "p.updDt = CURRENT_TIMESTAMP " +
            "WHERE p.id = :postId")
    void updateLikeCount(Long postId, Integer likeCount);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PostEntity p " +
            "SET p.commentCount = p.commentCount + 1, " +
            "p.updDt = CURRENT_TIMESTAMP " +
            "WHERE p.id = :id")
    void increaseCommentCount(Long id);

}