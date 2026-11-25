package com.algosync.backend.global.exception;

import com.algosync.backend.common.exception.ReviewJobNotFoundException;
import com.algosync.backend.common.exception.SubmissionProcessingException;
import com.algosync.backend.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러에서 발생한 알려진 예외를 공통 형식의 HTTP 오류 응답으로 변환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 외부 API 오류에 지정된 HTTP 상태와 오류 코드를 사용합니다.
    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponseDto> handleExternalApiException(ExternalApiException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponseDto(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ReviewJobNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleReviewJobNotFound(ReviewJobNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto("REVIEW_JOB_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(SubmissionProcessingException.class)
    public ResponseEntity<ErrorResponseDto> handleSubmissionProcessing(SubmissionProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto("SUBMISSION_PROCESSING_FAILED", e.getMessage()));
    }
}
