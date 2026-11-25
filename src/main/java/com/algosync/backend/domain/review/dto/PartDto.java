package com.algosync.backend.domain.review.dto;

import lombok.*;

/**
 * Gemini 메시지를 구성하는 텍스트 파트입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PartDto {
    private String text;
}
