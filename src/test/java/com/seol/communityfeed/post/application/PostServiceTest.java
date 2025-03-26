package com.seol.communityfeed.post.application;

import com.seol.communityfeed.fake.FakeObjectFactory;
import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.application.Dto.LikeRequestDto;
import com.seol.communityfeed.post.application.Dto.UpdatePostRequestDto;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.user.application.Dto.CreateUserRequestDto;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.domain.UserInfo;
import com.seol.communityfeed.user.repository.FakeUserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
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
        UpdatePostRequestDto updateDto = new UpdatePostRequestDto(
               user.getId(), updatedContent, PostPublicationState.PRIVATE);

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
        UpdatePostRequestDto updateDto = new UpdatePostRequestDto(
                 otherUser.getId(), updatedContent, PostPublicationState.PRIVATE);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> postService.updatePost(post.getId(), updateDto));
    }
}
