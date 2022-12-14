package com.wgilooy.blog.exception;

/**
 * status -> 401
 */
public class UnAuthorized extends BlogException {
    
    private static final String MESSAGE = "인증이 필요합니다.";

    public UnAuthorized() {
        super(MESSAGE);
    }

    public UnAuthorized(String field, String errorMessage) {
        super(MESSAGE);
        addValidation(field, errorMessage);
    }

    @Override
    public int statusCode() {
        return 401;
    }

}
