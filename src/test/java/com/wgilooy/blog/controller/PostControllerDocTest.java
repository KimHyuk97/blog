package com.wgilooy.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.repositroy.PostRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.wgilooy.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 조회")
    @Transactional
    void test1() throws Exception {

        Post post = Post.builder()
                        .content("단일 조회 테스트입니다.")
                        .title("단일조회12345678910")
                        .build();

        postRepository.save(post);

        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{postId}", post.getId())
                .accept(APPLICATION_JSON)) 
            .andExpect(status().isOk()) 
            .andDo(document("post_inquiry", 
                RequestDocumentation.pathParameters(
                    RequestDocumentation
                        .parameterWithName("postId")
                        .description("게시글 고유 ID")
                ),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("id").description("게시글 id"),
                    PayloadDocumentation.fieldWithPath("title").description("게시글 제목"),
                    PayloadDocumentation.fieldWithPath("content").description("게시글 내용")
                )
            ));
    }

    @Test
    @DisplayName("글 등록")
    @Transactional
    void test2() throws Exception {

        PostDTO request = PostDTO.builder()
                                .title("제목입니다.")
                                .content("내용입니다.")
                                .build();

        String json = objectMapper.writeValueAsString(request);

        System.err.println(json);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/posts")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andDo(document("post-create"
                        , 
                            PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("제목").optional(),
                                PayloadDocumentation.fieldWithPath("content").description("내용")
                            )
                        ));
    }


}
