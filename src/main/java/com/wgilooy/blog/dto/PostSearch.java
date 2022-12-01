package com.wgilooy.blog.dto;

import static java.lang.Math.min;
import static java.lang.Math.max;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10; 


    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);    
    }
}
