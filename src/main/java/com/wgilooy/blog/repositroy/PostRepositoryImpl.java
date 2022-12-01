package com.wgilooy.blog.repositroy;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostSearch;

import static com.wgilooy.blog.domain.QPost.post;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {                

        return jpaQueryFactory.selectFrom(post)
            .offset(postSearch.getOffset())
            .limit(postSearch.getSize())
            .orderBy(post.id.desc())
            .fetch();
    } 
    
}
