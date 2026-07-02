package com.algosync.backend.common.exception;

public class ReviewJobNotFoundException extends RuntimeException {
    public ReviewJobNotFoundException(String message) {
        super(message);
    }
}
