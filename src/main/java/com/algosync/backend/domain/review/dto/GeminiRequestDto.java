package com.algosync.backend.domain.review.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Gemini 요청에 전달할 메시지 목록을 담습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeminiRequestDto {
    private List<ContentDto> contents;

    public GeminiRequestDto(String prompt) {
        this.contents = new ArrayList<>();
        this.contents.add(new ContentDto(prompt));
    }
}
