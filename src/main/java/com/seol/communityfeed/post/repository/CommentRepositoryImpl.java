package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.post.application.Interface.CommentRepository;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.post.repository.entity.comment.CommentEntity;
import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.jpa.JpaCommentRepository;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;

    @Override
    public Comment save(Comment comment) {
        // authorEntity, postEntity는 실 프로젝트 상황에 맞게 주입 또는 조회
        UserEntity authorEntity = new UserEntity(comment.getAuthor());
        PostEntity postEntity = new PostEntity(comment.getPost(), authorEntity);

        if (comment.getId() != null) {
            jpaCommentRepository.updateComment(comment.getId(), comment.getContentObject().getContentText());
            return comment;
        }

        CommentEntity commentEntity = new CommentEntity(comment, authorEntity, postEntity);
        CommentEntity saved = jpaCommentRepository.save(commentEntity);
        return saved.toDomain(comment.getAuthor(), comment.getPost());
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = jpaCommentRepository.findById(id).orElseThrow();
        return commentEntity.toDomain(
                commentEntity.getAuthor().toDomain(),
                commentEntity.getPost().toDomain()
        );
    }
}

