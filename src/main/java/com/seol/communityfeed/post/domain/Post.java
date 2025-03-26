package com.seol.communityfeed.post.domain;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.post.domain.content.Content;
import com.seol.communityfeed.post.domain.content.PostContent;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class Post {

    private final Long id;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;
    private PostPublicationState state;

    public static Post createPost(Long id, User author, String content, PostPublicationState state){
        return new Post(id, author, new PostContent(content), new PositiveIntegerCounter(), state);
    }

    public static Post createDefaultPost(Long id, User author, String content){
        return new Post(id, author, new PostContent(content), new PositiveIntegerCounter(), PostPublicationState.PUBLIC);
    }

    public Post(Long id, User author, Content content){
        this(id, author, content, new PositiveIntegerCounter(), PostPublicationState.PUBLIC);
    }

    public Post(Long id, User author, Content content, PostPublicationState state){
        this(id, author, content, new PositiveIntegerCounter(), state);
    }

    public Post(Long id, User author, Content content, PositiveIntegerCounter likeCount, PostPublicationState state){
        if(author == null){
            throw new IllegalArgumentException("author는 null일 수 없습니다");
        }
        this.id = id;
        this.author = author;
        this.content = content;
        this.likeCount = likeCount != null ? likeCount : new PositiveIntegerCounter();
        this.state = state;
    }

    public void like() {
        likeCount.increase();
    }

    public void unlike() {
        likeCount.decrease();
    }

    public void updatePost(User user, String updateContent, PostPublicationState state){
        if(!this.author.equals(user)){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }

        this.state = state;
        this.content.updateContent(updateContent);
    }

    public String getContent() {
        return content.getContentText();
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }

    public Content getContentObject(){
        return content;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return Objects.equals(this.getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
