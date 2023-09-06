package com.wgilooy.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.dto.PostEidt;
import com.wgilooy.blog.dto.PostSearch;
import com.wgilooy.blog.response.PostResponse;
import com.wgilooy.blog.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public void write(@RequestBody @Valid PostDTO request) {
        request.isValid();
        postService.write(request);
    }

    @GetMapping("/api/posts/{postId}")
    public PostResponse get(@PathVariable(name="postId") Long id) {
        return postService.get(id);
    }

    @GetMapping("/api/posts")
    public List<PostResponse> posts() {
        return postService.posts();
    }

    @GetMapping("/api/getList")
    public List<PostResponse> getList(PostSearch postSearch) {

        return postService.getList(postSearch);
    }

    @PatchMapping("/api/posts/{id}")
    public PostResponse edit(@PathVariable Long id, @RequestBody @Valid PostEidt postEidt) {

        return postService.edit(id, postEidt);
    }

    @DeleteMapping("/api/posts/{id}")
    public void delete(@PathVariable Long id) {

        postService.delete(id);
    }
}
