package com.seol.communityfeed.post.application.Interface;

import com.seol.communityfeed.post.domain.comment.Comment;


public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);
}
