package com.wgilooy.blog.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.dto.PostEidt;
import com.wgilooy.blog.dto.PostSearch;
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
        List<PostResponse> posts = postService.posts();

        // then

        // null 체크
        Assertions.assertNotNull(posts);

        // Assertions.assertEquals();
    }

    @Test
    @DisplayName("pagination 테스트")
    void pagination() {
        //given
        List<Post> requestPost = IntStream.range(0, 30)  
            .mapToObj(i -> Post.builder()
                .content("내용입니다."+ i)
                .title("제목입니다."+i)
                .build())
            .collect(Collectors.toList());
                        
        postRepository.saveAll(requestPost);

        // when
        PostSearch postSearch = PostSearch.builder()
                                            .page(1)
                                            .build();
        
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(10L, posts.size());
        Assertions.assertEquals("제목입니다.29", posts.get(0).getTitle());
        Assertions.assertEquals("제목입니다.25", posts.get(4).getTitle());
    }

    @Test
    @DisplayName("pagination page가 0일 때 테스트")
    void pagination_0_test() {
        //given
        List<Post> requestPost = IntStream.range(0, 30)  
            .mapToObj(i -> Post.builder()
                .content("내용입니다."+ i)
                .title("제목입니다."+i)
                .build())
            .collect(Collectors.toList());
                        
        postRepository.saveAll(requestPost);

        // when
        PostSearch postSearch = PostSearch.builder()
                                            .page(0)
                                            .build();
        
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(10L, posts.size());
        Assertions.assertEquals("제목입니다.29", posts.get(0).getTitle());
        Assertions.assertEquals("제목입니다.25", posts.get(4).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void post_eidt_title() {
        //given
        Post requestPost = Post.builder()
                        .title("제목입니다.")
                        .content("내용")
                        .build();      
                        
        postRepository.save(requestPost);

        PostEidt postEidt = PostEidt.builder()
                                    .title("제목 변경")
                                    .content(requestPost.getContent())
                                    .build();

        // when
        postService.edit(requestPost.getId(), postEidt);

        Post changePost = postRepository.findById(requestPost.getId())
            .orElseThrow(()-> new RuntimeException("존재하지 않는 글입니다. id="+requestPost.getId()));

        // then
        Assertions.assertEquals("제목 변경", changePost.getTitle());
        Assertions.assertEquals("내용", changePost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void post_eidt_content() {
        //given
        Post requestPost = Post.builder()
                        .title("제목입니다.")
                        .content("내용입니다.")
                        .build();      
                        
        postRepository.save(requestPost);

        PostEidt postEidt = PostEidt.builder()
                                    .title(requestPost.getTitle())
                                    .content("내용입니다2")
                                    .build();

        // when
        postService.edit(requestPost.getId(), postEidt);

        Post changePost = postRepository.findById(requestPost.getId())
            .orElseThrow(()-> new RuntimeException("존재하지 않는 글입니다. id="+requestPost.getId()));

        // then
        Assertions.assertEquals("제목입니다.", changePost.getTitle());
        Assertions.assertEquals("내용입니다2", changePost.getContent());
    }


    
}
