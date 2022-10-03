package com.search.instagramsearching.exception;

import com.search.instagramsearching.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.search.instagramsearching.exception.ErrorCode.RESULT_NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ResponseDto> resultNotFoundExceptionHandler(ResultNotFoundException exception) {
        logger.error("resultNotFoundExceptionHandler", exception);
        return new ResponseEntity<>(ResponseDto.fail(RESULT_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> resultExpirationExceptionHandler(ResultExpirationException exception) {
        logger.error("resultExpirationExceptionHandler", exception);
        return new ResponseEntity<>(ResponseDto.fail(RESULT_NOT_FOUND), HttpStatus.GONE);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> invalidParameterExceptionHandler(InvalidParameterException exception) {
        logger.error("invalidParameterExceptionHandler", exception);
        return new ResponseEntity<>(ResponseDto.fail(RESULT_NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}