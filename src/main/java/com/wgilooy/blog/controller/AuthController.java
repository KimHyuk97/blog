package com.wgilooy.blog.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wgilooy.blog.dto.Login;
import com.wgilooy.blog.response.TokenResponse;
import com.wgilooy.blog.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/auth/login")
    public TokenResponse login(@RequestBody @Valid Login request) {
        String accessToken = authService.signin(request);

        return new TokenResponse(accessToken);
    }

}
