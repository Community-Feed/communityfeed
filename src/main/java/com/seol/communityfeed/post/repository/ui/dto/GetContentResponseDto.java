package com.seol.communityfeed.post.repository.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetContentResponseDto {
    private Long id;                   // 콘텐츠 ID (게시글 ID)
    private String content;           // 콘텐츠 내용
    private Long userId;              // 작성자 ID
    private String userName;          // 작성자 이름
    private String userProfileImage;  // 작성자 프로필 이미지 URL
    private LocalDateTime createdAt;  // 생성일시
    private LocalDateTime updatedAt;  // 수정일시
    private Integer likeCount;        // 좋아요 수
    private boolean isLikedByMe;      // 내가 좋아요 눌렀는지 여부
}
