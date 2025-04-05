package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.message.repository.application.MessageRepository;
import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.post.repository.entity.like.LikeEntity;
import com.seol.communityfeed.post.repository.entity.like.LikeIdEntity;
import com.seol.communityfeed.post.repository.entity.like.LikeTarget;
import com.seol.communityfeed.post.repository.jpa.JpaCommentRepository;
import com.seol.communityfeed.post.repository.jpa.JpaLikeRepository;
import com.seol.communityfeed.post.repository.jpa.JpaPostRepository;
import com.seol.communityfeed.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JpaPostRepository jpaPostRepository;
    private final JpaCommentRepository jpaCommentRepository;
    private final JpaLikeRepository jpaLikeRepository;
    private final MessageRepository messageRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean checkLike(Post post, User user) {
        LikeIdEntity likeId = buildLikeId(post.getId(), user.getId(), LikeTarget.POST);
        return jpaLikeRepository.existsById(likeId);
    }

    @Override
    @Transactional
    public void like(Post post, User user) {
        LikeIdEntity likeId = buildLikeId(post.getId(), user.getId(), LikeTarget.POST);

        if (jpaLikeRepository.existsById(likeId)) {
            // 중복 방지
            return;
        }

        jpaLikeRepository.save(new LikeEntity(post, user));
        jpaPostRepository.updateLikeCount(post.getId(), 1);
        messageRepository.sendLikeMessage(user, post.getAuthor());
    }

    @Override
    @Transactional
    public void unlike(Post post, User user) {
        LikeIdEntity likeId = buildLikeId(post.getId(), user.getId(), LikeTarget.POST);

        if (!jpaLikeRepository.existsById(likeId)) {
            // 존재하지 않는 좋아요에 대한 삭제 방지
            return;
        }

        jpaLikeRepository.deleteById(likeId);
        jpaPostRepository.updateLikeCount(post.getId(), -1);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkLike(Comment comment, User user) {
        LikeIdEntity likeId = buildLikeId(comment.getId(), user.getId(), LikeTarget.COMMENT);
        return jpaLikeRepository.existsById(likeId);
    }

    @Override
    @Transactional
    public void like(Comment comment, User user) {
        LikeIdEntity likeId = buildLikeId(comment.getId(), user.getId(), LikeTarget.COMMENT);

        if (jpaLikeRepository.existsById(likeId)) {
            return;
        }

        jpaLikeRepository.save(new LikeEntity(comment, user));
        jpaCommentRepository.updateLikeCount(comment.getId(), 1);
    }

    @Override
    @Transactional
    public void unlike(Comment comment, User user) {
        LikeIdEntity likeId = buildLikeId(comment.getId(), user.getId(), LikeTarget.COMMENT);

        if (!jpaLikeRepository.existsById(likeId)) {
            return;
        }

        jpaLikeRepository.deleteById(likeId);
        jpaCommentRepository.updateLikeCount(comment.getId(), -1);
    }

    /**
     * 내부 헬퍼 메서드 - 대소문자 포함 안전하게 LikeIdEntity 생성
     */
    private LikeIdEntity buildLikeId(Long targetId, Long userId, LikeTarget type) {
        return new LikeIdEntity(targetId, userId, type.name()); // Enum.name()은 항상 대문자
    }
}
