package com.wgilooy.blog.repositroy;

import java.util.List;

import com.wgilooy.blog.domain.Post;
import com.wgilooy.blog.dto.PostSearch;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
