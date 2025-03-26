package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.post.repository.entity.comment.CommentEntity;
import com.seol.communityfeed.post.repository.entity.like.LikeEntity;
import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.jpa.JpaCommentRepository;
import com.seol.communityfeed.post.repository.jpa.JpaLikeRepository;
import com.seol.communityfeed.post.repository.jpa.JpaPostRepository;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.entity.UserEntity;
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

    @Override
    @Transactional
    public boolean checkLike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
       // System.out.println("checkLike → id: " + likeEntity.getId());
        return jpaLikeRepository.existsById(likeEntity.getId());
    }

    @Override
    @Transactional
    public void like(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);

        // ✅ 로그 추가: id null 여부 확인
        /*System.out.println("🟡 likeEntity.getId(): " + likeEntity.getId());
        System.out.println("🟢 targetId: " + likeEntity.getId().getTargetId());
        System.out.println("🟢 userId: " + likeEntity.getId().getUserId());
        System.out.println("🟢 targetType: " + likeEntity.getId().getTargetType());*/

        entityManager.persist(likeEntity);
        //jpaLikeRepository.saveAndFlush(likeEntity);

       // System.out.println("✅ persist 호출 완료");

        UserEntity authorEntity = new UserEntity(post.getAuthor());
        PostEntity postEntity = new PostEntity(post, authorEntity);
        jpaPostRepository.updateLikeCount(post.getId(), 1);
    }

    @Override
    @Transactional
    public void unlike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);
        jpaLikeRepository.deleteById(likeEntity.getId());

        UserEntity authorEntity = new UserEntity(post.getAuthor());
        PostEntity postEntity = new PostEntity(post, authorEntity);
        jpaPostRepository.updateLikeCount(post.getId(), -1);
    }

    @Override
    @Transactional
    public boolean checkLike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        return jpaLikeRepository.existsById(likeEntity.getId());
    }

    @Override
    @Transactional
    public void like(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        entityManager.persist(likeEntity);
       // jpaLikeRepository.saveAndFlush(likeEntity);

        UserEntity authorEntity = new UserEntity(comment.getAuthor());
        PostEntity postEntity = new PostEntity(comment.getPost(), new UserEntity(comment.getPost().getAuthor()));
        CommentEntity commentEntity = new CommentEntity(comment, authorEntity, postEntity);
        jpaCommentRepository.updateLikeCount(comment.getId(), 1);
    }

    @Override
    @Transactional
    public void unlike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
        jpaLikeRepository.deleteById(likeEntity.getId());

        UserEntity authorEntity = new UserEntity(comment.getAuthor());
        PostEntity postEntity = new PostEntity(comment.getPost(), new UserEntity(comment.getPost().getAuthor()));
        CommentEntity commentEntity = new CommentEntity(comment, authorEntity, postEntity);
        jpaCommentRepository.updateLikeCount(comment.getId(), -1);
    }
}
