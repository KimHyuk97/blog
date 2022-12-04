package com.wgilooy.blog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEidtor.PostEidtorBuilder toEidtor() {
        return PostEidtor.builder()
            .title(title)
            .content(content);
    }


    // 글 수정
    public void edit(PostEidtor postEidtor) {
        title = postEidtor.getTitle();
        content = postEidtor.getContent();
    }
    
    
}
