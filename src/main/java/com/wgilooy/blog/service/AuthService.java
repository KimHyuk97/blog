package com.wgilooy.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wgilooy.blog.domain.Token;
import com.wgilooy.blog.domain.User;
import com.wgilooy.blog.dto.Login;
import com.wgilooy.blog.dto.Signup;
import com.wgilooy.blog.exception.LoginFailure;
import com.wgilooy.blog.repositroy.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login request) {

        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(LoginFailure::new);

        Token token = user.addToken();

        return token.getAccessToken();
    }

    @Transactional
    public void signup(Signup signup) {
        User user = User.builder()
                .email(signup.getEmail())
                .password(signup.getPassword())
                .build();
        userRepository.save(user);
    }

}