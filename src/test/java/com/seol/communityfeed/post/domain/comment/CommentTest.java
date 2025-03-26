package com.seol.communityfeed.post.domain.comment;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.CommentContent;
import com.seol.communityfeed.post.domain.content.PostContent;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
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
    private final Post post = new Post(1L, user, new PostContent("content"), PostPublicationState.PUBLIC);
    private final Comment comment = new Comment(1L, post, user, new CommentContent("content"), new PositiveIntegerCounter());

    @Test
    void givenCommentCreated_whenLike_thenLikeCountShouldIncrease() {
        // when
        comment.like();

        // then
        assertEquals(1, comment.getLikeCount());
    }

    @Test
    void givenCommentLiked_whenUnlike_thenLikeCountShouldDecrease() {
        // given
        comment.like();

        // when
        comment.unlike();

        // then
        assertEquals(0, comment.getLikeCount());
    }

    @Test
    void givenCommentNotLiked_whenUnlike_thenThrowException() {
        // when, then
        assertThrows(IllegalStateException.class, () -> comment.unlike());
    }
}
