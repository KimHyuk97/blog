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
import com.wgilooy.blog.dto.PostEidt;
import com.wgilooy.blog.repositroy.PostRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;

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


    @Test
    @DisplayName("/posts 글 단일 조회 (title 10자만 조회해보기)")
    void test03() throws Exception {
        Post post = Post.builder()
                        .content("단일 조회 테스트입니다.")
                        .title("단일조회12345678910")
                        .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{postId}", post.getId())
                            .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value("단일조회123456"))
                        .andExpect(jsonPath("$.content").value("단일 조회 테스트입니다."))
                        .andDo(print());
    }

    @Test
    @DisplayName("/posts 글 여러개 조회 (title 10자만 조회해보기)")
    void test04() throws Exception {
        postRepository.saveAll(List.of(
            Post.builder()
                .title("title01")
                .content("content01")
                .build(),

            Post.builder()
                .title("title02")
                .content("content02")
                .build()
        ));

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                            .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()", Matchers.is(2)))
                        .andExpect(jsonPath("$.[0].title").value("title01"))
                        .andExpect(jsonPath("$.[0].content").value("content01"))
                        .andExpect(jsonPath("$.[1].title").value("title02"))
                        .andExpect(jsonPath("$.[1].content").value("content02"))
                        .andDo(print());
    }

    @Test
    @DisplayName("/posts 글 여러개 조회 페이지 테스트")
    void test05() throws Exception {
        //given
        List<Post> requestPost = IntStream.range(0, 30)  
            .mapToObj(i -> Post.builder()
                .content("내용입니다."+ i)
                .title("제목입니다."+i)
                .build())
            .collect(Collectors.toList());
                        
        postRepository.saveAll(requestPost);
        
        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getList?page=1&size=10")
                            .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()", Matchers.is(10)))
                        .andDo(print());
    }

    @Test
    @DisplayName("/페이지가 0일 때 테스트")
    void test6() throws Exception {
        //given
        List<Post> requestPost = IntStream.range(0, 30)  
            .mapToObj(i -> Post.builder()
                .content("내용입니다."+ i)
                .title("제목입니다."+i)
                .build())
            .collect(Collectors.toList());
                        
        postRepository.saveAll(requestPost);
        
        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getList?page=0&size=10")
                            .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()", Matchers.is(10)))
                        .andDo(print());
    }



    @Test
    @DisplayName("/posts 글 제목 수정")
    void test7_title_edit() throws Exception {

        //1. 글 저장
        Post post = Post.builder()
                        .content("제목")
                        .title("내용")
                        .build();

        postRepository.save(post);

        // 2. 변경할 데이터 생성
        PostEidt postEidt = PostEidt.builder()
            .title("제목2")
            .content("내용")
            .build();

        String json = objectMapper.writeValueAsString(postEidt);
        
        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/posts/{id}", post.getId())    
                            .contentType(APPLICATION_JSON)
                            .content(json)
                        )
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value("제목2"))
                        .andExpect(jsonPath("$.content").value("내용"))
                        .andDo(print());
    }

    @Test
    @DisplayName("/posts 글 삭제")
    void test8_delete() throws Exception {

        //1. 글 저장
        Post post = Post.builder()
                        .content("제목")
                        .title("내용")
                        .build();

        postRepository.save(post);
        
        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}", post.getId())    
                            .contentType(APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andDo(print());
    }
}
