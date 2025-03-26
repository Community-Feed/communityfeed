package com.seol.communityfeed.post.repository.ui;

import com.seol.communityfeed.common.ui.Response;
import com.seol.communityfeed.post.application.CommentService;
import com.seol.communityfeed.post.application.Dto.*;
import com.seol.communityfeed.post.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Response<Long> createComment(@RequestBody CreateCommentRequestDto dto) {
        Comment comment = commentService.createComment(dto);
        return Response.ok(comment.getId());
    }

    @PatchMapping("/{commentId}")
    public Response<Long> updateComment(
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody UpdateCommentRequestDto dto) {

        // dto에 commentId가 이미 포함되어 있다고 가정
        Comment comment = commentService.updateContent(dto);
        return Response.ok(comment.getId());
    }
}
