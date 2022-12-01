package com.wgilooy.blog.service;

import java.util.List;
import java.util.stream.Collectors; 

import org.springframework.stereotype.Service;

import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.dto.PostSearch;
import com.wgilooy.blog.repositroy.PostRepository;
import com.wgilooy.blog.response.PostResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostDTO request) {

        Post post = Post.builder()
                        .content(request.getContent())
                        .title(request.getTitle())
                        .build();

        log.info("post={}", post);

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));
       
        return PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build();
    }

    public List<PostResponse> posts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponse :: new)
                .collect(Collectors.toList()); 
    }

    public List<PostResponse> getList(PostSearch postSearch) {
       
        return postRepository.getList(postSearch)
            .stream()
            .map(PostResponse :: new)
            .collect(Collectors.toList());
    }

}
