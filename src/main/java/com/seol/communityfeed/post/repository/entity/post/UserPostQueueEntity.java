package com.seol.communityfeed.post.repository.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community_user_post_queue")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserPostQueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long postId;
    private Long authorId;

    public UserPostQueueEntity(Long userId, Long postId, Long authorId){
        this.userId = userId;
        this.postId = postId;
        this.authorId = authorId;
    }
}
