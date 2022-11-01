package com.wgilooy.blog.service;

import org.springframework.stereotype.Service;

import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.repositroy.PostRepository;

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

}
