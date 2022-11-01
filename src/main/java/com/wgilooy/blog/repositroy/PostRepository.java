package com.wgilooy.blog.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wgilooy.blog.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
