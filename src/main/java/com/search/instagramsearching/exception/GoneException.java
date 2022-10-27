package com.search.instagramsearching.exception;

public class GoneException extends RuntimeException {
    private final ErrorCode errorCode;

    public GoneException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
