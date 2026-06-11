package com.algosync.backend.domain.review.dto;

import lombok.*;

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
