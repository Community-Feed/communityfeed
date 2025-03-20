package com.seol.communityfeed.post.domain.content;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.junit.jupiter.api.Assertions.*;

class CommentContentTest {

    @Test
    void givenValidContent_whenCreated_thenNoException(){
        // given
        String content = "This is a valid comment";

        // when
        CommentContent commentContent = new CommentContent(content);

        // then
        assertEquals(content, commentContent.getContentText());
    }

    @Test
    void givenTooLongContent_whenCreated_thenThrowException(){
        // given
        String content = "a".repeat(101); // 101자 초과

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenNullOrEmptyContent_whenCreated_thenThrowException(String invalidContent){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(invalidContent));
    }

    @Test
    void givenValidContent_whenUpdated_thenShouldUpdate(){
        // given
        CommentContent commentContent = new CommentContent("Initial comment");

        // when
        commentContent.updateContent("Updated comment");

        // then
        assertEquals("Updated comment", commentContent.getContentText());
    }

    @Test
    void givenTooLongContent_whenUpdated_thenThrowException(){
        // given
        CommentContent commentContent = new CommentContent("Initial comment");

        // when, then
        assertThrows(IllegalArgumentException.class, () -> commentContent.updateContent("a".repeat(101)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenNullOrEmptyContent_whenUpdated_thenThrowException(String invalidContent){
        // given
        CommentContent commentContent = new CommentContent("Initial comment");

        // when, then
        assertThrows(IllegalArgumentException.class, () -> commentContent.updateContent(invalidContent));
    }
}
