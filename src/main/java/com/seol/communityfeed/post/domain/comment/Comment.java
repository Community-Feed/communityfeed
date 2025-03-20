package com.seol.communityfeed.post.domain.comment;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.Content;
import com.seol.communityfeed.user.domain.User;

public class Comment {

    private final Long id;
    private final Post post;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;

    public Comment(Long id, Post post, User author, Content content) {

        if(author == null){
            throw new IllegalArgumentException();
        }

        if(post == null){
            throw new IllegalArgumentException();
        }

        if(content == null){
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
    }

    public void like(User user){
        if(this.author.equals(user)){
            throw new IllegalArgumentException();
        }
        likeCount.increase();
    }

    public void unlike(){
        this.likeCount.decrease();
    }

    public void updateComment(User user, String updatedContent){
        if(!this.author.equals(user)){
            throw new IllegalArgumentException();
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
