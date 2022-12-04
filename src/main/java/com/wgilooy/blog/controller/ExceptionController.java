package com.wgilooy.blog.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wgilooy.blog.exception.BlogException;
import com.wgilooy.blog.response.ErrorResponse;

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

        return respone;
    }

    // @ResponseBody
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // @ExceptionHandler(PostNotFound.class)
    // public ErrorResponse PostNotFound(PostNotFound e) {
    //     ErrorResponse respone = ErrorResponse.builder()
    //                                          .code("404")
    //                                          .message(e.getMessage())
    //                                          .build();
        
    //     log.error("respone={}", respone);                       
    //     return respone;
    // }

    // @ResponseBody
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(InValidException.class)
    // public ErrorResponse InValidException(InValidException e) {
    //     ErrorResponse respone = ErrorResponse.builder()
    //                                          .code("400")
    //                                          .message(e.getMessage())
    //                                          .build();
        
    //     log.error("respone={}", respone);                       
    //     return respone;
    // }

    @ResponseBody
    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorResponse> BlogException(BlogException e) {
        ErrorResponse body = ErrorResponse.builder()
                                             .code(String.valueOf(e.statusCode()))
                                             .message(e.getMessage())
                                             .validation(e.getValidation())
                                             .build();     
                                                                                                                                                        
        return ResponseEntity.status(e.statusCode())
        .body(body);
    }
    
}
