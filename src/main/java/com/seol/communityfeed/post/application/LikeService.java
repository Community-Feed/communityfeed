package com.seol.communityfeed.post.application;

import com.seol.communityfeed.post.application.Interface.CommentRepository;
import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.application.Interface.PostRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final LikeRepository likeRepository;

    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId);
        User user = userService.getUser(userId);
        if (!likeRepository.checkLike(post, user)) {
            post.like();
            likeRepository.like(post, user);
        }
    }

    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId);
        User user = userService.getUser(userId);
        if (likeRepository.checkLike(post, user)) {
            post.unlike();
            likeRepository.unlike(post, user);
        }
    }

    public void likeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId);
        User user = userService.getUser(userId);
        if (!likeRepository.checkLike(comment, user)) {
            comment.like();
            likeRepository.like(comment, user);
        }
    }

    public void unlikeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId);
        User user = userService.getUser(userId);
        if (likeRepository.checkLike(comment, user)) {
            comment.unlike();
            likeRepository.unlike(comment, user);
        }
    }
}
