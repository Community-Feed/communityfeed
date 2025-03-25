package com.seol.communityfeed.post.domain;

import com.seol.communityfeed.post.domain.content.PostContent;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    private final UserInfo info = new UserInfo("name", "url");
    private final User user = new User(1L, info);
    private final User otherUser = new User(2L, info);
    private final Post post = new Post(1L, user, new PostContent("content"), PostPublicationState.PUBLIC);

    @Test
    void givenPostCreated_whenLike_thenLikeCountShouldBe1(){
        //when
        post.like(otherUser);

        //then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenLikeByAuthor_thenThrowException(){
        //when, then
        assertThrows(IllegalArgumentException.class, () -> post.like(user));
    }

    @Test
    void givenPostLiked_whenUnlike_thenLikeCountShouldBe0(){
        // given
        post.like(otherUser);

        // when
        post.unlike(otherUser);

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenUpdatedByAuthor_thenShouldUpdateContent(){
        // given
        String updatedContent = "updated content";

        // when
        post.updatePost(user, updatedContent, PostPublicationState.PRIVATE);

        // then
        assertEquals(updatedContent, post.getContent());
        assertEquals(PostPublicationState.PRIVATE, post.getState());
    }

    @Test
    void givenPostCreated_whenUpdatedByOtherUser_thenThrowException(){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> post.updatePost(otherUser, "hacked content", PostPublicationState.PRIVATE));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenPostCreated_whenUpdatedWithEmptyContent_thenThrowException(String emptyContent){
        // when, then
        assertThrows(IllegalStateException.class, () -> post.updatePost(user, emptyContent, PostPublicationState.PUBLIC));
    }

    @Test
    void givenPostCreated_whenStateUpdated_thenStateShouldChange(){
        // when
        post.updatePost(user, "updated content", PostPublicationState.PRIVATE);

        // then
        assertEquals(PostPublicationState.PRIVATE, post.getState());

        // when
        post.updatePost(user, "another update", PostPublicationState.PUBLIC);

        // then
        assertEquals(PostPublicationState.PUBLIC, post.getState());
    }
}