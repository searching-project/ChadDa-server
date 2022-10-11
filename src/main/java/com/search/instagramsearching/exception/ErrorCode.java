package com.search.instagramsearching.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_PARAMETER(400, null, "Invalid Request Data"),
    RESULT_EXPIRATION(410, "R001", "Result Was Expired"),
    RESULT_NOT_FOUND(404, "R002", "Result Not Found"),

    // 유저 관련 오류
    USER_NOT_FOUND(404, "U001", "User Not Found"),

    // 게시글 관련 오류
    POST_NOT_FOUND(404, "P001", "Post Not Found");

    private final int status;
    private final String code;
    private final String message;
}