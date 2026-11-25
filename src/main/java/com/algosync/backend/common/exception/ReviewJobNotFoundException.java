package com.algosync.backend.common.exception;

/**
 * 요청한 토큰으로 리뷰 작업을 찾을 수 없을 때 사용합니다.
 */
public class ReviewJobNotFoundException extends RuntimeException {
    public ReviewJobNotFoundException(String message) {
        super(message);
    }
}
