package com.search.instagramsearching.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400, null, "Invalid Request Data"),
    RESULT_EXPIRATION(410, "R001", "Result Was Expired"),
    RESULT_NOT_FOUND(404, "R002", "Result Not Found");

    private final String code;
    private final String message;
    private final int status;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}