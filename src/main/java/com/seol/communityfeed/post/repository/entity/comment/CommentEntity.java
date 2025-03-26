package com.seol.communityfeed.post.repository.entity.comment;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.common.repository.entity.TimeBaseEntity;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.post.domain.content.CommentContent;
import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

@Entity
@Table(name = "community_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class CommentEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="authorId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name="postId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PostEntity post;

    private String content;

    private Integer likeCount;

    // ✅ 도메인 객체 → 엔티티 변환 생성자
    public CommentEntity(Comment comment, UserEntity authorEntity, PostEntity postEntity) {
        this.id = comment.getId();
        this.author = authorEntity;
        this.post = postEntity;
        this.content = comment.getContentObject().getContentText();
        this.likeCount = comment.getLikeCount();
    }

    // ✅ 엔티티 → 도메인 객체 변환 메서드
    public Comment toDomain(User author, Post post) {
        return Comment.builder()
                .id(this.id)
                .author(author)
                .post(post)
                .content(new CommentContent(this.content))
                .likeCount(new PositiveIntegerCounter(this.likeCount, 1000))
                .build();
    }
}
