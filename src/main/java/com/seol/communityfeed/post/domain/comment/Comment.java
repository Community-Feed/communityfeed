package com.seol.communityfeed.post.domain.comment;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.CommentContent;
import com.seol.communityfeed.post.domain.content.Content;
import com.seol.communityfeed.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comment {
    private final Long id;
    private final Post post;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;

    public static Comment createComment(Post post, User author, String content){
        return Comment.builder()
                .id(null)
                .post(post)
                .author(author)
                .content(new CommentContent(content))
                .likeCount(new PositiveIntegerCounter())
                .build();
    }

    // Builder가 사용하는 모든 필드를 포함한 생성자
    public Comment(Long id, Post post, User author, Content content, PositiveIntegerCounter likeCount) {
        if (author == null || post == null || content == null) {
            throw new IllegalArgumentException("작성자, 게시글, 내용은 null일 수 없습니다.");
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCount = likeCount != null ? likeCount : new PositiveIntegerCounter();
    }

    public void like() {
        likeCount.increase();
    }

    public void unlike() {
        if (likeCount.getCount() == 0) {
            throw new IllegalStateException("좋아요 수는 0보다 작아질 수 없습니다.");
        }
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

    public Content getContentObject() {
        return content;
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }
}
