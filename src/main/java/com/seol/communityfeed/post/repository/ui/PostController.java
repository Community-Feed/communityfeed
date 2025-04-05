package com.seol.communityfeed.post.repository.ui;

import com.seol.communityfeed.common.ui.Response;
import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.application.Dto.UpdatePostRequestDto;
import com.seol.communityfeed.post.application.PostService;
import com.seol.communityfeed.post.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Long> createPost(@RequestBody CreatePostRequestDto dto){
        Post post = postService.createPost(dto);
        return Response.ok(post.getId());
    }

    @PatchMapping("/{postId}")
    public Response<Long> updatePost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody UpdatePostRequestDto dto) {
        Post post = postService.updatePost(postId, dto);
        return Response.ok(post.getId());
    }

}
