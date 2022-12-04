package com.wgilooy.blog.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEidtor {
    
    private final String title;

    private final String content;

    @Builder
    public PostEidtor(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
