package com.algosync.backend.domain.review.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Gemini 응답에 포함된 후보 목록을 담습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeminiResponseDto {
    private List<CandidateDto> candidates;

    public GeminiResponseDto(String text) {
        this.candidates = new ArrayList<>();
        this.candidates.add(new CandidateDto(new ContentDto(text)));
    }
}
