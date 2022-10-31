package com.wgilooy.blog.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
    private final List<Validation> validation = new ArrayList<>();

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
