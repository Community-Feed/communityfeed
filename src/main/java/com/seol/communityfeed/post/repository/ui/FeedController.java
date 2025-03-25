package com.seol.communityfeed.post.repository.ui;

import com.seol.communityfeed.common.ui.Response;
import com.seol.communityfeed.post.repository.post_queue.UserPostQueueQueryRepository;
import com.seol.communityfeed.post.repository.ui.dto.GetPostContentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final UserPostQueueQueryRepository queueQueryRepository;

    @GetMapping("/{userId}")
    public Response<List<GetPostContentResponseDto>> getPostFeed(@PathVariable(name = "userId")Long userId, Long lastPostId){
        List<GetPostContentResponseDto> result = queueQueryRepository.getContentResponse(userId, lastPostId);
        return Response.ok(result);
    }
}
