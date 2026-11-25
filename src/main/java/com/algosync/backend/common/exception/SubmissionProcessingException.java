package com.algosync.backend.common.exception;

/**
 * 제출 처리 과정에서 리뷰 결과를 생성하지 못했을 때 사용합니다.
 */
public class SubmissionProcessingException extends RuntimeException {
    public SubmissionProcessingException(String message) {
        super(message);
    }
}
