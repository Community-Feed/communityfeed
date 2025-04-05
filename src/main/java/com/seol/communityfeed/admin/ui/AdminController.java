package com.seol.communityfeed.admin.ui;

import com.seol.communityfeed.admin.ui.dto.GetTableListResponse;
import com.seol.communityfeed.admin.ui.dto.users.GetUserTableRequestDto;
import com.seol.communityfeed.admin.ui.dto.users.GetUserTableResponseDto;
import com.seol.communityfeed.admin.ui.query.AdminTableQueryRepository;
import com.seol.communityfeed.admin.ui.query.UserStatsQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserStatsQueryRepository userStatsQueryRepository;
    private final AdminTableQueryRepository adminTableQueryRepository;

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        modelAndView.addObject("result", userStatsQueryRepository.getDailyRegisterUserStats(7));
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView users(GetUserTableRequestDto dto){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("users");

        GetTableListResponse<GetUserTableResponseDto> result = adminTableQueryRepository.getUserTableData(dto);
        modelAndView.addObject("requestDto", dto);
        modelAndView.addObject("userList", result.getTableDate());
        modelAndView.addObject("totalCount", result.getTotalCount());
        return modelAndView;

    }
}
