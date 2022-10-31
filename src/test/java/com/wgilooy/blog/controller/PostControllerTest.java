package com.wgilooy.blog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/post 요청시 hello 를 출력한다.")
    void testPost1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\": \"제목\", \"content\": \"내용\"}")
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string("{}"))
                        .andDo(print());
    }

    @Test
    @DisplayName("/post 요청시 title값은 필수다.")
    void testPost2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\": \"                 \", \"content\": \"        \"}")
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("400"))
                        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                        .andExpect(jsonPath("$.validation[0].field").value("title"))
                        .andExpect(jsonPath("$.validation[0].errorMessage").value("title값은 필수다."))
                        .andExpect(jsonPath("$.validation[1].field").value("content"))
                        .andExpect(jsonPath("$.validation[1].errorMessage").value("content값은 필수다."))

                        .andDo(print());
    }
}
