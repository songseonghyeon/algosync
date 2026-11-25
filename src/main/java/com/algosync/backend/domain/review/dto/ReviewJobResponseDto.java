package com.algosync.backend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 비동기 리뷰의 조회 토큰, 상태, 결과를 전달합니다.
 */
@Getter
@AllArgsConstructor
public class ReviewJobResponseDto {
    private Long submissionId;
    private String reviewToken;
    private String status;
    private ReviewResponseDto review;

    // 대기 중인 작업에는 리뷰 결과가 아직 없습니다.
    public static ReviewJobResponseDto pending(Long submissionId, String reviewToken) {
        return new ReviewJobResponseDto(submissionId, reviewToken, "PENDING", null);
    }

    // 완료된 작업에는 생성된 리뷰 결과를 포함합니다.
    public static ReviewJobResponseDto completed(Long submissionId, String reviewToken, ReviewResponseDto review) {
        return new ReviewJobResponseDto(submissionId, reviewToken, "COMPLETED", review);
    }

    public static ReviewJobResponseDto failed(Long submissionId, String reviewToken) {
        return new ReviewJobResponseDto(submissionId, reviewToken, "FAILED", null);
    }
}
