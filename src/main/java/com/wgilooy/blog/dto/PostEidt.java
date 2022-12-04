package com.wgilooy.blog.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEidt {

    @NotBlank(message = "title값은 필수다.")
    private String title;

    @NotBlank(message = "content값은 필수다.")
    private String content;

    @Builder
    public PostEidt(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
