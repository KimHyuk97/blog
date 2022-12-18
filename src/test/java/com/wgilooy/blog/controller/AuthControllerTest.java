package com.wgilooy.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgilooy.blog.domain.Token;
import com.wgilooy.blog.domain.User;
import com.wgilooy.blog.dto.Login;
import com.wgilooy.blog.repositroy.UserRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인 성공")
    void loginTest() throws Exception {

        userRepository.save(User.builder()
            .email("wgilooy97@naver.com")
            .password("1111")
            .build());

        Login request = Login.builder()
                                .email("wgilooy97@naver.com")
                                .password("1111")
                                .build();

        String json = objectMapper.writeValueAsString(request);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                            .contentType(APPLICATION_JSON)
                            .content(json)
                        )
                    .andExpect(status().isOk())                        
                    .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 토근 응답")
    void loginTest2() throws Exception {

        userRepository.save(User.builder()
            .email("wgilooy97@naver.com")
            .password("1111")
            .build());

        Login request = Login.builder()
                                .email("wgilooy97@naver.com")
                                .password("1111")
                                .build();

        String json = objectMapper.writeValueAsString(request);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                            .contentType(APPLICATION_JSON)
                            .content(json)
                        )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists())
                    .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 인증이 필요한 필요한 페이지에 접속한다")
    @Transactional
    void loginTest3() throws Exception {
        User user = User.builder()
            .email("wgilooy97@naver.com")
            .password("1111")
            .build();

        userRepository.save(user);

        Token token = user.addToken();
        
        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                            .header("Authorization", token.getAccessToken())
                            .contentType(APPLICATION_JSON)
                        )
                    .andExpect(status().isOk())
                    .andDo(print());
    }

    @Test
    @DisplayName("로그인 검증 실패로 인해 페이지 접속 불가")
    @Transactional
    void loginTest4() throws Exception {
        User user = User.builder()
            .email("wgilooy97@naver.com")
            .password("1111")
            .build();

        userRepository.save(user);

        Token token = user.addToken();
        
        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                            .header("Authorization", token.getAccessToken()+"_error")
                            .contentType(APPLICATION_JSON)
                        )
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
    }
}
