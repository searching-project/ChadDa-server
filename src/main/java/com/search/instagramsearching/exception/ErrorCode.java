package com.search.instagramsearching.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_PARAMETER(400, null, "Invalid Request Data"),
    RESULT_EXPIRATION(410, "R001", "Result Was Expired"),
    RESULT_NOT_FOUND(404, "R002", "Result Not Found");

    private final int status;
    private final String code;
    private final String message;
}