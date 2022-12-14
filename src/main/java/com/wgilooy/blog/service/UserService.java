package com.wgilooy.blog.service;

import org.springframework.stereotype.Service;

import com.wgilooy.blog.domain.User;
import com.wgilooy.blog.dto.Login;
import com.wgilooy.blog.exception.LoginFailure;
import com.wgilooy.blog.repositroy.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User login(Login loginUser) {

        User user = userRepository.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword())
            .orElseThrow(LoginFailure:: new);

        return user;
    }
    
}