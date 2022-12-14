package com.wgilooy.blog.service;

import java.util.List;
import java.util.stream.Collectors; 

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.domain.PostEidtor;
import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.dto.PostEidt;
import com.wgilooy.blog.dto.PostSearch;
import com.wgilooy.blog.exception.PostNotFound;
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
            .orElseThrow(PostNotFound::new);
       
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

    @Transactional
    public PostResponse edit(Long id, PostEidt postEidt) {

        System.err.println(postEidt);

        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFound::new);

        // 빌더 활용
        // PostEidtor.PostEidtorBuilder eidtorBuilder = post.toEidtor();

        // if(postEidt.getTitle() != null) {
        //     eidtorBuilder.title(postEidt.getTitle());
        // }

        // if(postEidt.getContent() != null) {
        //     eidtorBuilder.content(postEidt.getContent());
        // }

        // post.edit(eidtorBuilder.build());

        PostEidtor postEidtor = post.toEidtor()
            .title(postEidt.getTitle())
            .content(postEidt.getContent())
            .build();
        
        post.edit(postEidtor);

        return new PostResponse(post);
    }


    public void delete(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }

}
