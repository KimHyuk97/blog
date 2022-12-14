package com.wgilooy.blog.exception;

public class LoginFailure extends BlogException {
    
    private static final String MESSAGE = "로그인 실패";

    public LoginFailure() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }

}
