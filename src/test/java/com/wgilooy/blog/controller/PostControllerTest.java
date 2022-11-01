package com.wgilooy.blog.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.repositroy.PostRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    // 각각 테스트 메소드들이 실행이 될때 항상 수행이 되도록하는 메소드
    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }
    

    @Test
    @DisplayName("/post 요청시 title값은 필수다.")
    void test01() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                            .contentType(APPLICATION_JSON)
                            .content("{\"title\": \"                 \", \"content\": \"        \"}"))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("400"))
                        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                        // .andExpect(jsonPath("$.validation[0].field").value("title"))
                        // .andExpect(jsonPath("$.validation[0].errorMessage").value("title값은 필수다."))
                        // .andExpect(jsonPath("$.validation[1].field").value("content"))
                        // .andExpect(jsonPath("$.validation[1].errorMessage").value("content값은 필수다."))

                        .andDo(print());
    }

    @Test
    @DisplayName("/post 요청시 db에 값을 저장시킨다.")
    void test02() throws Exception {
        PostDTO request = PostDTO.builder()
                                .title("제목입니다.")
                                .content("내용입니다.")
                                .build();

        String json = objectMapper.writeValueAsString(request);

        System.err.println(json);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                        .andExpect(status().isOk())
                        .andDo(print());

        // then
        Assertions.assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);

        Assertions.assertEquals(1L, postRepository.count());
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }
}
