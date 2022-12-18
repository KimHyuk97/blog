package com.wgilooy.blog.repositroy;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.wgilooy.blog.domain.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByAccessToken(String accessToken);
    
    
}
