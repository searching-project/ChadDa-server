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
    POST_NOT_FOUND(404, "P001", "Post Not Found"),

    // 로그인 관련 오류
    INVALID_LOGIN(401, "A001", "Unauthorized"),
    INVALID_ACCESS_TOKEN(401, "A002", "Unauthorized"),

    EXPIRED_ACCESS_TOKEN(404, "A003", "Access Token Was Expired"),
    REFRESH_TOKEN_NOT_FOUND(404, "A007", "Refresh Token Not Found"),
    INVALID_REFRESH_TOKEN(401, "A005", "Unauthorized"),
    ACCESS_TOKEN_NOT_FOUND(404, "A004", "Access Token Not Found"),
    EXPIRED_REFRESH_TOKEN(404, "A006", "Refresh Token Was Expired");




    private final int status;
    private final String code;
    private final String message;
}