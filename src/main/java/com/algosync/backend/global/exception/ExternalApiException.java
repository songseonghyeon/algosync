package com.algosync.backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ExternalApiException extends RuntimeException {
    private final String code;
    private final HttpStatusCode status;

    private ExternalApiException(HttpStatusCode status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    private ExternalApiException(HttpStatusCode status, String code, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
    }

    public static ExternalApiException geminiEmptyResponse() {
        return new ExternalApiException(
                HttpStatus.BAD_GATEWAY,
                "GEMINI_EMPTY_RESPONSE",
                "AI 리뷰 응답이 비어 있습니다."
        );
    }

    public static ExternalApiException geminiServiceUnavailable(Throwable cause) {
        return new ExternalApiException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "GEMINI_SERVICE_UNAVAILABLE",
                "Gemini API를 일시적으로 사용할 수 없습니다.",
                cause
        );
    }

    public static ExternalApiException geminiReviewFailed(Throwable cause) {
        return new ExternalApiException(
                HttpStatus.BAD_GATEWAY,
                "GEMINI_REVIEW_FAILED",
                "AI 리뷰 생성에 실패했습니다.",
                cause
        );
    }
    public static ExternalApiException nvidiaReviewFailed(Throwable cause) {
        return new ExternalApiException(
                HttpStatus.BAD_GATEWAY,
                "NVIDIA_REVIEW_FAILED",
                "NVIDIA AI 리뷰 생성에 실패했습니다.",
                cause
        );
    }
}
