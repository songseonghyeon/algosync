package com.algosync.backend.domain.review.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
