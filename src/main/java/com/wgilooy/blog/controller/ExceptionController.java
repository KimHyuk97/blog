package com.wgilooy.blog.controller;


import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wgilooy.blog.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    // MethodArgumentNotValidException 필드 에러 내용
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResponse respone = ErrorResponse.builder()
                                             .code("400")
                                             .message("잘못된 요청입니다.")
                                             .build();

        for (FieldError error : e.getFieldErrors()) {
            respone.addValidation(error.getField(), error.getDefaultMessage());
        }

        log.error("respone={}", respone);
        return respone;
    }
    
}
