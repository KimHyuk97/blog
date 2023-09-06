package com.wgilooy.blog.controller;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgilooy.blog.domain.User;
import com.wgilooy.blog.dto.Login;
import com.wgilooy.blog.repositroy.UserRepository;

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

}
