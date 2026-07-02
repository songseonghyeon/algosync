package com.algosync.backend.global.exception;

import com.algosync.backend.common.exception.ReviewJobNotFoundException;
import com.algosync.backend.common.exception.SubmissionProcessingException;
import com.algosync.backend.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
