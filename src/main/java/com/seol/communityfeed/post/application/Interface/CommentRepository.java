package com.seol.communityfeed.post.application.Interface;

import com.seol.communityfeed.post.domain.comment.Comment;

import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
}
