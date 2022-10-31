package com.wgilooy.blog.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDTO {

    // NotBlank 빈값 or null 체크
    @NotBlank(message = "title값은 필수다.")
    private String title;

    @NotBlank(message = "content값은 필수다.")
    private String content;
}
