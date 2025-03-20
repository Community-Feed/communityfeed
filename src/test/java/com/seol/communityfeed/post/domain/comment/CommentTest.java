package com.seol.communityfeed.post.domain.comment;

import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.CommentContent;
import com.seol.communityfeed.post.domain.content.PostContent;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private final UserInfo info = new UserInfo("name", "url");
    private final User user = new User(1L, info);
    private final User otherUser = new User(2L, info);
    private final Post post = new Post(1L, user, new PostContent("content"));
    private final Comment comment = new Comment(1L, post, user, new CommentContent("content"));

    @Test
    void givenCommentCreated_whenLike_thenLikeCountShouldIncrease(){
        // when
        comment.like(otherUser);

        // then
        assertEquals(1, comment.getLikeCount());
    }

    @Test
    void givenCommentCreated_whenLikeByAuthor_thenThrowException(){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.like(user));
    }

    @Test
    void givenCommentLiked_whenUnlike_thenLikeCountShouldDecrease(){
        // given
        comment.like(otherUser);

        // when
        comment.unlike();

        // then
        assertEquals(0, comment.getLikeCount());
    }

    @Test
    void givenCommentCreated_whenUpdatedByAuthor_thenShouldUpdateContent(){
        // given
        String updatedContent = "Updated comment";

        // when
        comment.updateComment(user, updatedContent);

        // then
        assertEquals(updatedContent, comment.getContent().getContentText());
    }

    @Test
    void givenCommentCreated_whenUpdatedByOtherUser_thenThrowException(){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.updateComment(otherUser, "Hacked comment"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenCommentCreated_whenUpdatedWithEmptyContent_thenThrowException(String invalidContent){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> comment.updateComment(user, invalidContent));
    }

    @Test
    void givenCommentLiked_whenDoubleLike_thenThrowException(){
        // given
        comment.like(otherUser);

        // when, then
        assertThrows(IllegalStateException.class, () -> comment.like(otherUser));
    }
}