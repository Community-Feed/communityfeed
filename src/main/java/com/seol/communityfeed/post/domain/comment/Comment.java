package com.seol.communityfeed.post.domain.comment;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.CommentContent;
import com.seol.communityfeed.post.domain.content.Content;
import com.seol.communityfeed.user.domain.User;

import java.util.HashSet;
import java.util.Set;

public class Comment {
    private final Long id;
    private final Post post;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;
    private final Set<User> likedUsers; // 좋아요한 사용자 목록

    public static Comment createComment(Post post, User author, String content){
        return new Comment(null, post, author, new CommentContent(content));
    }

    public Comment(Long id, Post post, User author, Content content) {
        if (author == null || post == null || content == null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
        this.likedUsers = new HashSet<>(); // 중복 방지를 위한 Set 사용
    }

    public void like(User user) {
        if (this.author.equals(user)) {
            throw new IllegalArgumentException("자신의 댓글에는 좋아요를 누를 수 없습니다.");
        }
        if (likedUsers.contains(user)) {
            throw new IllegalStateException("이미 좋아요를 누른 댓글입니다."); // 중복 좋아요 방지
        }
        likedUsers.add(user); // 사용자 추가
        likeCount.increase();
    }

    public void unlike(User user) {
        if (!likedUsers.contains(user)) {
            throw new IllegalStateException("좋아요를 누르지 않은 댓글입니다.");
        }
        likedUsers.remove(user); // 좋아요 취소
        likeCount.decrease();
    }

    public void updateComment(User user, String updatedContent) {
        if (!this.author.equals(user)) {
            throw new IllegalArgumentException("작성자만 댓글을 수정할 수 있습니다.");
        }
        this.content.updateContent(updatedContent);
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getAuthor() {
        return author;
    }

    public Content getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }
}