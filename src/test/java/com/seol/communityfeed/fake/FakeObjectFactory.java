package com.seol.communityfeed.fake;

import com.seol.communityfeed.post.application.CommentService;
import com.seol.communityfeed.post.application.Interface.CommentRepository;
import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.application.Interface.PostRepository;
import com.seol.communityfeed.post.application.PostService;
import com.seol.communityfeed.post.domain.repository.FakeCommentRepository;
import com.seol.communityfeed.post.domain.repository.FakeLikeRepository;
import com.seol.communityfeed.post.domain.repository.FakePostRepository;
import com.seol.communityfeed.user.application.Interface.UserRelationRepository;
import com.seol.communityfeed.user.application.Interface.UserRepository;
import com.seol.communityfeed.user.application.UserRelationService;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.repository.FakeUserRelationRepository;
import com.seol.communityfeed.user.repository.FakeUserRepository;


public class FakeObjectFactory {

    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();
    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final CommentRepository fakeCommentRepository = new FakeCommentRepository();
    private static final LikeRepository fakeLikeRepository = new FakeLikeRepository();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationService = new UserRelationService(userService, fakeUserRelationRepository);
    private static final PostService postService = new PostService(userService, fakePostRepository, fakeLikeRepository);
    private static final CommentService commentService = new CommentService(userService, postService, fakeCommentRepository, fakeLikeRepository);

    private FakeObjectFactory() {}

    public static UserService getUserService() {
        return userService;
    }

    public static UserRelationService getUserRelationService() {
        return userRelationService;
    }

    public static PostService getPostService() {
        return postService;
    }

    public static CommentService getCommentService() {
        return commentService;
    }
}