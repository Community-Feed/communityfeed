package com.seol.communityfeed.admin.ui.query;

import com.seol.communityfeed.admin.ui.dto.users.GetDailyRegisterUserResponseDto;

import java.util.List;

public interface UserStatsQueryRepository {
    List<GetDailyRegisterUserResponseDto> getDailyRegisterUserStats(int beforeDays);
}
