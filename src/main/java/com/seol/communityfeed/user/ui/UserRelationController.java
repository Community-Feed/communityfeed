package com.seol.communityfeed.user.ui;

import com.seol.communityfeed.common.ui.Response;
import com.seol.communityfeed.user.application.Dto.FollowUserRequestDto;
import com.seol.communityfeed.user.application.UserRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relation")
@RequiredArgsConstructor
public class UserRelationController {

    private final UserRelationService relationService;

    @PostMapping("/follow")
    public Response<Void> followUser(@RequestBody FollowUserRequestDto dto){
        relationService.follow(dto);
        return Response.ok(null);
    }

    @PostMapping("/unfollow")
    public Response<Void> unfollowUser(@RequestBody FollowUserRequestDto dto){
        relationService.unfollow(dto);
        return Response.ok(null);
    }
}
