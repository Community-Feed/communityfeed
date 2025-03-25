package com.seol.communityfeed.post.repository.entity.post;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.common.repository.entity.TimeBaseEntity;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.Content;
import com.seol.communityfeed.post.domain.content.PostContent;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;

@Entity
@Table(name = "community_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@DynamicUpdate
public class PostEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="author_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    private String content;

    @Convert(converter = PostPublicationStateConverter.class)
    private PostPublicationState state;

    private Integer likeCount;

    @ColumnDefault("0")
    private int commentCount;

    public PostEntity(Post post, UserEntity authorEntity) {
        this.id = post.getId();
        this.author = authorEntity; // ✅ 영속 상태의 UserEntity 주입
        this.content = post.getContent();
        this.state = post.getState();
        this.likeCount = post.getLikeCount();
    }

    public Post toPost() {
        return Post.builder()
                .id(this.id)
                .author(this.author.toUser())
                .content(new PostContent(this.content))
                .state(this.state)
                .likeCount(new PositiveIntegerCounter(
                        this.likeCount != null ? this.likeCount : 0,
                        1000 // 기본 최대값
                ))
                .build();
    }

    public Post toDomain() {
        return Post.builder()
                .id(this.id)
                .author(this.author.toDomain())  // author도 변환 필요
                .content(new PostContent(this.content))
                .state(this.state)
                .likeCount(new PositiveIntegerCounter(this.likeCount, 1000))
                .likedUsers(new HashSet<>())  // DB엔 저장 안함
                .build();
    }
}
