package com.wgilooy.blog.repositroy;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.wgilooy.blog.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
   
}
