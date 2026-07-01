package com.algosync.backend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewJobResponseDto {
    private Long submissionId;
    private String reviewToken;
    private String status;
    private ReviewResponseDto review;

    public static ReviewJobResponseDto pending(Long submissionId, String reviewToken) {
        return new ReviewJobResponseDto(submissionId, reviewToken, "PENDING", null);
    }

    public static ReviewJobResponseDto completed(Long submissionId, String reviewToken, ReviewResponseDto review) {
        return new ReviewJobResponseDto(submissionId, reviewToken, "COMPLETED", review);
    }

    public static ReviewJobResponseDto failed(Long submissionId, String reviewToken) {
        return new ReviewJobResponseDto(submissionId, reviewToken, "FAILED", null);
    }
}
