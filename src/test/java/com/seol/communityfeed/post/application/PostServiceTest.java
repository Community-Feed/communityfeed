package com.seol.communityfeed.post.application;

import com.seol.communityfeed.fake.FakeObjectFactory;
import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.application.Dto.LikeRequestDto;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.user.application.Dto.CreateUserRequestDto;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {

    private final UserService userService = FakeObjectFactory.getUserService();
    private final PostService postService = FakeObjectFactory.getPostService();

    private final User user = userService.createUser(new CreateUserRequestDto("user1", null));
    private final User otherUser = userService.createUser(new CreateUserRequestDto("user2", null));

    private final CreatePostRequestDto dto =
            new CreatePostRequestDto(user.getId(), "this is test content", PostPublicationState.PUBLIC);

    @Test
    @Order(1)
    void givenValidRequest_whenCreatePost_thenPostShouldBeCreated() {
        // when
        Post post = postService.createPost(dto);

        // then
        assertNotNull(post.getId());
        assertEquals(user, post.getAuthor());
        assertEquals("this is test content", post.getContent());
        assertEquals(PostPublicationState.PUBLIC, post.getState());
    }

    @Test
    @Order(2)
    void givenPostCreated_whenGetPost_thenReturnPost() {
        // given
        Post post = postService.createPost(dto);

        // when
        Post foundPost = postService.getPost(post.getId());

        // then
        assertEquals(post, foundPost);
    }

    @Test
    @Order(3)
    void givenPostCreated_whenUpdatePostByAuthor_thenUpdateSuccess() {
        // given
        Post post = postService.createPost(dto);
        String updatedContent = "this is updated content";
        CreatePostRequestDto updateDto = new CreatePostRequestDto(user.getId(), updatedContent, PostPublicationState.PRIVATE);

        // when
        Post updatedPost = postService.updatePost(post.getId(), updateDto);

        // then
        assertEquals(updatedContent, updatedPost.getContent());
        assertEquals(PostPublicationState.PRIVATE, updatedPost.getState());
    }

    @Test
    @Order(4)
    void givenPostCreated_whenUpdatePostByOtherUser_thenThrowException() {
        // given
        Post post = postService.createPost(dto);
        String updatedContent = "unauthorized update";
        CreatePostRequestDto updateDto = new CreatePostRequestDto(otherUser.getId(), updatedContent, PostPublicationState.PRIVATE);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> postService.updatePost(post.getId(), updateDto));
    }

    @Test
    @Order(5)
    void givenPostCreated_whenLikedByOtherUser_thenLikeCountShouldIncrease() {
        // given
        Post post = postService.createPost(dto);
        LikeRequestDto likeRequest = new LikeRequestDto(post.getId(), otherUser.getId());

        // when
        postService.likePost(likeRequest);

        // then
        assertEquals(1, postService.getPost(post.getId()).getLikeCount());
    }

    @Test
    @Order(6)
    void givenPostLiked_whenLikedAgainBySameUser_thenLikeShouldNotDuplicate() {
        // given
        Post post = postService.createPost(dto);
        LikeRequestDto likeRequest = new LikeRequestDto(post.getId(), otherUser.getId());
        postService.likePost(likeRequest);

        // when
        postService.likePost(likeRequest); // 중복 좋아요 요청

        // then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    @Order(7)
    void givenPostLiked_whenUnliked_thenLikeCountShouldDecrease() {
        // given
        Post post = postService.createPost(dto);
        LikeRequestDto likeRequest = new LikeRequestDto(post.getId(), otherUser.getId());
        postService.likePost(likeRequest);

        // when
        postService.unlikePost(likeRequest);

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    @Order(8)
    void givenPostNotLiked_whenUnlike_thenDoNothing() {
        // given
        Post post = postService.createPost(dto);
        LikeRequestDto likeRequest = new LikeRequestDto(post.getId(), otherUser.getId());

        // when
        postService.unlikePost(likeRequest); // 좋아요 없이 취소 요청

        // then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    @Order(9)
    void givenPostCreated_whenLikedByAuthor_thenThrowException() {
        // given
        Post post = postService.createPost(dto);
        LikeRequestDto likeRequest = new LikeRequestDto(post.getId(), user.getId());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> postService.likePost(likeRequest));
    }
}
