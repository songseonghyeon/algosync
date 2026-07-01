package com.algosync.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReviewJobNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleReviewJobNotFound(ReviewJobNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse("리뷰 조회 실패", e.getMessage()));
    }

    @ExceptionHandler(SubmissionProcessingException.class)
    public ResponseEntity<ApiErrorResponse> handleSubmissionProcessing(SubmissionProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse("코드 제출 처리 실패", e.getMessage()));
    }
}
