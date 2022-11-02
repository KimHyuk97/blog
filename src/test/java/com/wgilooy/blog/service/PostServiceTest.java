package com.wgilooy.blog.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.repositroy.PostRepository;
import com.wgilooy.blog.response.PostResponse;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }
    
    @Test
    @DisplayName("글 작성")
    void testWrite() {
        // given
        PostDTO postDto = PostDTO.builder()
                        .content("내용입니다.")
                        .title("제목입니다.")
                        .build();                        
                        
        // when
        postService.write(postDto);

        // then
        Assertions.assertEquals(1L, postRepository.count());

    }

    @Test
    @DisplayName("글 1개 조회")
    void testGet() {
        //given
        Post requestPost = Post.builder()
                        .content("내용입니다.")
                        .title("제목입니다.")
                        .build();      
                        
        postRepository.save(requestPost);

        // when
        PostResponse post = postService.get(requestPost.getId());

        // then

        // null 체크
        Assertions.assertNotNull(post);

        Assertions.assertEquals(post.getId(), requestPost.getId());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void testGetList() {
        //given
        Post requestPost = Post.builder()
                        .content("내용입니다.")
                        .title("제목입니다.")
                        .build();      
                        
        postRepository.save(requestPost);

        Post requestPost2 = Post.builder()
                        .content("내용입니다.2")
                        .title("제목입니다.2")
                        .build();      
                        
        postRepository.save(requestPost2);

        // when
        List<PostResponse> posts = postService.getList();

        // then

        // null 체크
        Assertions.assertNotNull(posts);

        // Assertions.assertEquals();
    }
}
