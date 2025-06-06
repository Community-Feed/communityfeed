package com.seol.communityfeed.user.ui;

import com.seol.communityfeed.common.principal.AuthPrincipal;
import com.seol.communityfeed.common.ui.Response;
import com.seol.communityfeed.user.application.Dto.CreateUserRequestDto;
import com.seol.communityfeed.user.application.Dto.GetUserListResponseDto;
import com.seol.communityfeed.user.application.Dto.GetUserResponseDto;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.jpa.JpaUserListQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JpaUserListQueryRepository userListQueryRepository;

    @PostMapping
    public Response<Long> createUser(@RequestBody CreateUserRequestDto dto){
        User user = userService.createUser(dto);
        return Response.ok(user.getId());
    }

    @GetMapping("/{userId}")
    public Response<GetUserResponseDto> getUserProfile(@PathVariable(name="userId")Long userId){
        return Response.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/{userId}/following")
    public Response<List<GetUserListResponseDto>> getFollowingList(@PathVariable(name = "userId")Long userId){
        List<GetUserListResponseDto> result = userListQueryRepository.getFollowingUserList(userId);
        return Response.ok(result);
    }

    @GetMapping("/{userId}/follower")
    public Response<List<GetUserListResponseDto>> getFollowerList(@PathVariable(name = "userId")Long userId){
        List<GetUserListResponseDto> result = userListQueryRepository.getFollowerUserList(userId);
        return Response.ok(result);
    }

    // ✅ 로그인한 유저 정보 조회
    @GetMapping("/me")
    public Response<GetUserResponseDto> getMyProfile(@AuthPrincipal Long userId) {
        return Response.ok(userService.getUserProfile(userId));
    }
}
