package com.algosync.backend.global.dto;

/**
 * 클라이언트에 오류 코드와 메시지를 전달합니다.
 */
public record ErrorResponseDto(String code, String message) {
}
