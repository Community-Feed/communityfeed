package com.seol.communityfeed.post.repository.ui;

import com.seol.communityfeed.common.idempotency.Idempotent;
import com.seol.communityfeed.common.ui.Response;
import com.seol.communityfeed.post.application.LikeService;
import com.seol.communityfeed.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @Idempotent
    @PostMapping("/post/{postId}/like")
    public Response<Void> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        likeService.likePost(postId, userId);
        return Response.ok(null);
    }

    @PostMapping("/post/{postId}/unlike")
    public Response<Void> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        likeService.unlikePost(postId, userId);
        return Response.ok(null);
    }

    @Idempotent
    @PostMapping("/comment/{commentId}/like")
    public Response<Void> likeComment(@PathVariable Long commentId, @RequestParam Long userId) {
        likeService.likeComment(commentId, userId);
        return Response.ok(null);
    }

    @PostMapping("/comment/{commentId}/unlike")
    public Response<Void> unlikeComment(@PathVariable Long commentId, @RequestParam Long userId) {
        likeService.unlikeComment(commentId, userId);
        return Response.ok(null);
    }
}
