package com.algosync.backend.domain.review.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Gemini 메시지의 본문을 여러 파트로 표현합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContentDto {
    private List<PartDto> parts;

    // 편의를 위해 문자열을 받으면 바로 PartDto 리스트로 조립해 주는 생성자
    public ContentDto(String text) {
        this.parts = new ArrayList<>();
        this.parts.add(new PartDto(text));
    }
}
