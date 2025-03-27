package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.user.domain.User;

import java.util.*;

public class FakeLikeRepository implements LikeRepository {

    private final Map<Post, Set<User>> postLikes = new HashMap<>();
    private final Map<Comment, Set<User>> commentLikes = new HashMap<>();

    @Override
    public boolean checkLike(Post post, User user) {
        return postLikes.getOrDefault(post, Collections.emptySet()).contains(user);
    }

    @Override
    public void like(Post post, User user) {
        Set<User> users = postLikes.computeIfAbsent(post, k -> new HashSet<>());
        users.add(user);
    }

    @Override
    public void unlike(Post post, User user) {
        Set<User> users = postLikes.get(post);
        if (users != null) {
            users.remove(user);
        }
    }

    @Override
    public boolean checkLike(Comment comment, User user) {
        return commentLikes.getOrDefault(comment, Collections.emptySet()).contains(user);
    }

    @Override
    public void like(Comment comment, User user) {
        Set<User> users = commentLikes.computeIfAbsent(comment, k -> new HashSet<>());
        users.add(user);
    }

    @Override
    public void unlike(Comment comment, User user) {
        Set<User> users = commentLikes.get(comment);
        if (users != null) {
            users.remove(user);
        }
    }
}
