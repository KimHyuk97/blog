package com.wgilooy.blog.exception;

public class PostNotFound extends BlogException {

    private static final String MESSAGE = "존재하지 않는 글입니다";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
    
}
