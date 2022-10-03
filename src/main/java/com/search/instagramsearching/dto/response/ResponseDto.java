
package com.search.instagramsearching.dto.response;

import com.search.instagramsearching.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class ResponseDto<T> { //T가 아니고 A/B어떤거든 상관없음 제네릭임을 선언
    private boolean success;
    private T data; //제네릭의 변수 선언방법
    private Error error;

    public ResponseDto(boolean b, Object o, String code, String message) {
    }

    @AllArgsConstructor
    @Getter
    public static class Error {
        private String code;
        private String message;
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(ErrorCode errorCode) {
        return new ResponseDto<>(false, null, errorCode.getCode(), errorCode.getMessage());
    }
}