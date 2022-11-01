package com.wgilooy.blog.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDTO {

    // NotBlank 빈값 or null 체크
    @NotBlank(message = "title값은 필수다.")
    private String title;

    @NotBlank(message = "content값은 필수다.")
    private String content;


    @Builder
    public PostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 빌더의 장점
     * 
     * - 가독성
     * - 필요한 값만 받을 수 있다. => 오버로딩 가능한 조건 찾아보기!
     * - 객체의 불변성
     */
    


}
