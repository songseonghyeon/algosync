package com.algosync.backend.domain.review.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
