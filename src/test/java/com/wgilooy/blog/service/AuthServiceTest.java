package com.wgilooy.blog.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wgilooy.blog.domain.User;
import com.wgilooy.blog.dto.Signup;
import com.wgilooy.blog.repositroy.UserRepository;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    void signupTest() {
        // given
        Signup signup = Signup.builder()
                .email("wgilooy97@naver.com")
                .password("1111")
                .build();

        // when
        authService.signup(signup);

        // then
        Assertions.assertEquals(1L, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        Assertions.assertEquals("wgilooy97@naver.com", user.getEmail());
        Assertions.assertEquals("1111", user.getPassword());
    }

}
