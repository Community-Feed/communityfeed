package com.seol.communityfeed.post.application.Interface;

import com.seol.communityfeed.post.domain.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    Post findById(Long id);

    List<Post> findAll();
}
