package com.seol.communityfeed.post.application;

import com.seol.communityfeed.common.domain.PositiveIntegerCounter;
import com.seol.communityfeed.post.application.Interface.CommentRepository;
import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.application.Interface.PostRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.domain.comment.Comment;
import com.seol.communityfeed.post.domain.content.CommentContent;
import com.seol.communityfeed.post.domain.content.PostContent;
import com.seol.communityfeed.post.domain.content.PostPublicationState;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * 좋아요 기능 테스트 추가
 * Mockito 사용 이유:
 *  - LikeService의 로직을 독립적으로 테스트하기 위해 외부 의존성을 Mock 처리
 *  - DB 연동 없이 다양한 분기 상황을 시뮬레이션 가능
 *  - 메서드 호출 여부 검증을 통한 정확한 로직 테스트 (verify 사용)
 */
@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock private PostRepository postRepository;
    @Mock private CommentRepository commentRepository;
    @Mock private UserService userService;
    @Mock private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    private final UserInfo info = new UserInfo("tester", "profile.jpg");
    private final User author = new User(1L, info);
    private final User otherUser = new User(2L, info);

    // POST 테스트

    // 좋아요하지 않은 게시글에 좋아요를 누르면 좋아요 수가 증가해야 함
    @Test
    void givenNotLikedPost_whenLike_thenIncreaseLikeCount() {
        Post post = Post.createPost(1L, author, "게시글콘텐트테스트", PostPublicationState.PUBLIC);
        given(postRepository.findById(1L)).willReturn(post);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(post, otherUser)).willReturn(false);

        likeService.likePost(1L, 2L);

        assertEquals(1, post.getLikeCount());
        verify(likeRepository).like(eq(post), eq(otherUser));
    }

    //이미 좋아요한 게시글에 다시 좋아요를 누르면 아무 일도 일어나지 않아야 함
    @Test
    void givenAlreadyLikedPost_whenLikeAgain_thenDoNothing() {
        Post post = Post.createPost(1L, author, "게시글콘텐트테스트", PostPublicationState.PUBLIC);
        given(postRepository.findById(1L)).willReturn(post);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(post, otherUser)).willReturn(true);

        likeService.likePost(1L, 2L);

        assertEquals(0, post.getLikeCount());
        verify(likeRepository, never()).like(eq(post), eq(otherUser));
    }

    //좋아요한 게시글을 취소하면 좋아요 수가 감소해야 함
    @Test
    void givenLikedPost_whenUnlike_thenDecreaseLikeCount() {
        Post post = Post.createPost(1L, author, "게시글콘텐트테스트", PostPublicationState.PUBLIC);
        post.like();
        given(postRepository.findById(1L)).willReturn(post);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(post, otherUser)).willReturn(true);

        likeService.unlikePost(1L, 2L);

        assertEquals(0, post.getLikeCount());
        verify(likeRepository).unlike(eq(post), eq(otherUser));
    }

    //좋아요하지 않은 게시글을 취소하면 아무 일도 일어나지 않아야 함
    @Test
    void givenNotLikedPost_whenUnlike_thenDoNothing() {
        Post post = Post.createPost(1L, author, "게시글콘텐트테스트", PostPublicationState.PUBLIC);
        given(postRepository.findById(1L)).willReturn(post);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(post, otherUser)).willReturn(false);

        likeService.unlikePost(1L, 2L);

        assertEquals(0, post.getLikeCount());
        verify(likeRepository, never()).unlike(eq(post), eq(otherUser));
    }

    //COMMENT 테스트

    //좋아요하지 않은 댓글에 좋아요를 누르면 좋아요 수가 증가해야 함
    @Test
    void givenNotLikedComment_whenLike_thenIncreaseLikeCount() {
        Comment comment = new Comment(1L, new Post(1L, author, new PostContent("post테스트"), new PositiveIntegerCounter(), PostPublicationState.PUBLIC),
                author, new CommentContent("comment테스트"), new PositiveIntegerCounter());
        given(commentRepository.findById(1L)).willReturn(comment);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(comment, otherUser)).willReturn(false);

        likeService.likeComment(1L, 2L);

        assertEquals(1, comment.getLikeCount());
        verify(likeRepository).like(eq(comment), eq(otherUser));
    }

    //이미 좋아요한 댓글에 다시 좋아요를 누르면 아무 일도 일어나지 않아야 함
    @Test
    void givenAlreadyLikedComment_whenLikeAgain_thenDoNothing() {
        Comment comment = new Comment(1L, new Post(1L, author, new PostContent("post테스트"), new PositiveIntegerCounter(), PostPublicationState.PUBLIC),
                author, new CommentContent("comment테스트"), new PositiveIntegerCounter());
        given(commentRepository.findById(1L)).willReturn(comment);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(comment, otherUser)).willReturn(true);

        likeService.likeComment(1L, 2L);

        assertEquals(0, comment.getLikeCount());
        verify(likeRepository, never()).like(eq(comment), eq(otherUser));
    }

    //좋아요한 댓글을 취소하면 좋아요 수가 감소해야 함
    @Test
    void givenLikedComment_whenUnlike_thenDecreaseLikeCount() {
        PositiveIntegerCounter counter = new PositiveIntegerCounter();
        counter.increase();
        Comment comment = new Comment(1L, new Post(1L, author, new PostContent("post테스트"), counter, PostPublicationState.PUBLIC),
                author, new CommentContent("comment테스트"), counter);
        given(commentRepository.findById(1L)).willReturn(comment);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(comment, otherUser)).willReturn(true);

        likeService.unlikeComment(1L, 2L);

        assertEquals(0, comment.getLikeCount());
        verify(likeRepository).unlike(eq(comment), eq(otherUser));
    }

    //좋아요하지 않은 댓글을 취소하면 아무 일도 일어나지 않아야 함
    @Test
    void givenNotLikedComment_whenUnlike_thenDoNothing() {
        Comment comment = new Comment(1L, new Post(1L, author, new PostContent("post테스트"), new PositiveIntegerCounter(), PostPublicationState.PUBLIC),
                author, new CommentContent("comment테스트"), new PositiveIntegerCounter());
        given(commentRepository.findById(1L)).willReturn(comment);
        given(userService.getUser(2L)).willReturn(otherUser);
        given(likeRepository.checkLike(comment, otherUser)).willReturn(false);

        likeService.unlikeComment(1L, 2L);

        assertEquals(0, comment.getLikeCount());
        verify(likeRepository, never()).unlike(eq(comment), eq(otherUser));
    }
}