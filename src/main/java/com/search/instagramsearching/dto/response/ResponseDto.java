
package com.search.instagramsearching.dto.response;

import com.search.instagramsearching.exception.ErrorCode;
import com.search.instagramsearching.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {       //T가 아니고 A/B어떤거든 상관없음 제네릭임을 선언

    private boolean success;
    private T data;         //제네릭의 변수 선언방법
    private ErrorResponse errorResponse;

    // Response 성공시 - data 담아 보내주기
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    // Response 실패시 - 오류 정보 담아 보내주기
    public static <T> ResponseDto<T> fail(ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return new ResponseDto<>(false, null, errorResponse);
    }
}