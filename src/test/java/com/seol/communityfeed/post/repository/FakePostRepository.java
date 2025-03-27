package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.post.application.Interface.PostRepository;
import com.seol.communityfeed.post.domain.Post;

import java.util.*;

public class FakePostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private long idSequence = 1L;

    @Override
    public Post save(Post post) {
        if (post.getId() != null) {
            store.put(post.getId(), post);
            return post;
        }

        long id = idSequence++;
        Post newPost = new Post(id, post.getAuthor(), post.getContentObject(), post.getState()); // ✅ state 포함
        store.put(id, newPost);

        System.out.println("✅ 게시글 저장됨 - ID: " + id);
        return newPost;
    }

    @Override
    public Post findById(Long id) {
        System.out.println("🔍 findById 호출 - ID: " + id);
        return store.get(id);
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }
}