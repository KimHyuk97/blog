package com.wgilooy.blog.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final List<Validation> validation = new ArrayList<>();

    
    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String field, String errorMessage) {
        validation.add(new Validation(field, errorMessage));
    }

    @Getter
    @RequiredArgsConstructor
    private class Validation {
        private final String field;
        private final String errorMessage;
    }
}
