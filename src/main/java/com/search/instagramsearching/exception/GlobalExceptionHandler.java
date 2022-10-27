package com.search.instagramsearching.exception;

import com.search.instagramsearching.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.search.instagramsearching.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    protected ResponseEntity<?> notFoundExceptionHandler(NotFoundException exception) {
        logger.error("resultNotFoundExceptionHandler", exception);
        return new ResponseEntity<>(ResponseDto.fail(exception.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<?> goneExceptionHandler(GoneException exception) {
        logger.error("resultExpirationExceptionHandler", exception);
        return new ResponseEntity<>(ResponseDto.fail(exception.getErrorCode()), HttpStatus.GONE);
    }
}