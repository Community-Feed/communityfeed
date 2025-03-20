package com.seol.communityfeed.post.domain;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.post.domain.content.PostContent;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.user.domain.User;

public class Post {

    private final Long id;
    private final User author;
    private final PostContent content;
    private final PositiveIntegerCounter likeCount;
    private PostPublicationState state;

    public Post(Long id, User author, PostContent content){

        if(author == null){
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
        this.state = PostPublicationState.PUBLIC;
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

    public void updatePost(User user, String updateContent, PostPublicationState state){
        if(!this.author.equals(user)){
            throw new IllegalArgumentException();
        }

        this.state = state;
        this.content.updateContent(updateContent);
    }


    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content.getContentText();
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }

    public PostPublicationState getState() {
        return state;
    }
}
