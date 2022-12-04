package com.wgilooy.blog.dto;

import javax.validation.constraints.NotBlank;

import com.wgilooy.blog.exception.InValidException;

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

    public void isValid() {
        if(title.contains("바보")) {
            throw new InValidException("title", "제목에 금지어가 포함되어있습니다('바보')");
        }
    }

    /**
     * 빌더의 장점
     * 
     * - 가독성
     * - 필요한 값만 받을 수 있다. => 오버로딩 가능한 조건 찾아보기!
     * - 객체의 불변성
     */
    


}
