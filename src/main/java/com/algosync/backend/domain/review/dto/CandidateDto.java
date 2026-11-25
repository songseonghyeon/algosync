package com.algosync.backend.domain.review.dto;

import lombok.*;

/**
 * Gemini가 반환한 개별 응답 후보의 본문을 담습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CandidateDto {
    private ContentDto content;
}
