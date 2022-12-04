package com.wgilooy.blog.exception;

public class InValidException extends BlogException {

    private static final String MESSAGE = "금지된 단어 입니다.";

    public InValidException() {
        super(MESSAGE);
    }

    public InValidException(String field, String errorMessage) {
        super(MESSAGE);
        addValidation(field, errorMessage);
    }

    @Override
    public int statusCode() {
        return 400;
    }
    
}
