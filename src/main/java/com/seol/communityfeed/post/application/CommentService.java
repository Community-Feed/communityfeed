package com.seol.communityfeed.post.application;

import com.seol.communityfeed.post.application.Dto.CreateCommentRequestDto;
import com.seol.communityfeed.post.application.Dto.UpdateCommentRequestDto;
import com.seol.communityfeed.post.application.Interface.CommentRepository;
import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public CommentService(UserService userService, PostService postService, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

   public Comment getComment(Long id){
        return commentRepository.findById(id);
   }

   public Comment createComment(CreateCommentRequestDto dto){
        Post post = postService.getPost(dto.postId());
        User user = userService.getUser(dto.userId());

        Comment comment= Comment.createComment(post, user, dto.content());
        return commentRepository.save(comment);
   }

   public Comment updateContent(UpdateCommentRequestDto dto){
        Comment comment = getComment(dto.commentId());
        User user = userService.getUser(dto.userId());

        comment.updateComment(user, dto.content());
        return commentRepository.save(comment);
   }

}
