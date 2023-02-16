package com.wgilooy.blog.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Signup {
    @NotBlank(message = "이메일 누락")
    private String email;
    @NotBlank(message = "비밀번호 누락")
    private String password;
}
