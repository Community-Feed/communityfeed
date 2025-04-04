package com.seol.communityfeed.auth.application.dto;

public record UserAccessTokenResponseDto(String accessToken, Long userId) {
}
