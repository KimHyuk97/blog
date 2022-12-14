package com.wgilooy.blog.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wgilooy.blog.domain.User;
import com.wgilooy.blog.dto.Login;
import com.wgilooy.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    
    @PostMapping("/auth/login")
    public User login(@RequestBody @Valid Login loginUser) {
        User user = userService.login(loginUser);

        return user;
    }

}
