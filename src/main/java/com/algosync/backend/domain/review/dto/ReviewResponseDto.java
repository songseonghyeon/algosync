package com.algosync.backend.domain.review.dto;

import lombok.*;

/**
 * AI가 생성한 복잡도 분석, 피드백, 점수 등을 전달합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewResponseDto {
    String timeComplexity;
    String spaceComplexity;
    String feedback;
    String score;
}
