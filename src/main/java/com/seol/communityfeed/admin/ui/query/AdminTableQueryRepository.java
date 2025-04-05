package com.seol.communityfeed.admin.ui.query;

import com.seol.communityfeed.admin.ui.dto.GetTableListResponse;
import com.seol.communityfeed.admin.ui.dto.users.GetUserTableRequestDto;
import com.seol.communityfeed.admin.ui.dto.users.GetUserTableResponseDto;

public interface AdminTableQueryRepository {
    GetTableListResponse<GetUserTableResponseDto> getUserTableData(GetUserTableRequestDto dto);
}
