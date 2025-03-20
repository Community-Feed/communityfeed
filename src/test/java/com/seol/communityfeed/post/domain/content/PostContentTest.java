package com.seol.communityfeed.post.domain.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class PostContentTest {

    @Test
    void givenContentLengthIsOk_whenCreated_thenReturnTextContent(){
        //given
        String text = "this is a test";

        //when
        PostContent content = new PostContent(text);

        //then
        assertEquals(text, content.getContentText());
    }

    @Test
    void givenContentLengthIsOver_whenCreated_thenThrowError(){
        //given
        String content = "a".repeat(501);

        //when, then
        assertThrows(IllegalStateException.class, () -> new PostContent(content));
    }

    @ParameterizedTest
    @ValueSource(strings = {"뷁", "닭", "굵", "찱", "숡"})
    void givenContentLengthIsOverAndKorean_whenCreated_thenThrowError(String koreanWord) {
        // given
        final String content = koreanWord.repeat(501);

        // when, then
        assertThrows(IllegalStateException.class, () -> new PostContent(content));
    }

    @Test
    void givenContentLengthUnder_whenCreated_thenThrowError(){
        //given
        String content = "a".repeat(4);

        //when, then
        assertThrows(IllegalStateException.class, () -> new PostContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentIsEmpty_whenCreated_thenThrowError(String value){
        //when, then
        assertThrows(IllegalStateException.class, () -> new PostContent(value));
    }

    @Test
    void givenContentLengthIsOk_whenUpdated_thenNotThrowError(){
        //given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        //when
        postContent.updateContent("this is an updated content");

        //then
        assertEquals("this is an updated content", postContent.getContentText());
    }

    @Test
    void givenContentLengthIsOver_whenUpdated_thenThrowError(){
        //given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        //when, then
        assertThrows(IllegalStateException.class, () -> postContent.updateContent("a".repeat(501)));
    }

    @Test
    void givenContentLengthUnder_whenUpdated_thenThrowError(){
        //given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        //when, then
        assertThrows(IllegalStateException.class, () -> postContent.updateContent("a".repeat(4)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentIsEmpty_whenUpdated_thenThrowError(String value){
        //given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        //when, then
        assertThrows(IllegalStateException.class, () -> postContent.updateContent(value));
    }

    @Test
    void givenSameContent_whenUpdated_thenNotThrowError(){
        //given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        //when
        postContent.updateContent(content);

        //then
        assertEquals(content, postContent.getContentText());
    }
}