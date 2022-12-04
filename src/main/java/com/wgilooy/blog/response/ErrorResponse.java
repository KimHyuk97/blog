package com.wgilooy.blog.response;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    
    // 비어있는 값은 넘어가지 않는다.
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> validation;

    
    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String field, String errorMessage) {
        validation.put(field, errorMessage);
    }
}
