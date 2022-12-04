package com.wgilooy.blog.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public abstract class BlogException extends RuntimeException {

    private Map<String, String> validation = new HashMap<>();

    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

    public void addValidation(String field, String errorMessage) {
        validation.put(field, errorMessage);
    }
    
}
