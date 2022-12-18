package com.wgilooy.blog.response;

import lombok.Getter;

@Getter
public class TokenResponse {

    private final String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }    
    
}
