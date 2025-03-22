package com.seol.communityfeed.post.application;

import com.seol.communityfeed.post.application.Dto.CreatePostRequestDto;
import com.seol.communityfeed.post.application.Dto.LikeRequestDto;
import com.seol.communityfeed.post.application.Interface.LikeRepository;
import com.seol.communityfeed.post.application.Interface.PostRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.user.application.UserService;
import com.seol.communityfeed.user.domain.User;

public class PostService {

    private final UserService userService;

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    public PostService(UserService userService, PostRepository postRepository, LikeRepository likeRepository){
        this.userService = userService;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Post not found"));
    }

    public Post createPost(CreatePostRequestDto dto){
        User author = userService.getUser(dto.userId());
        Post post = Post.createPost(null, author, dto.content(), dto.state());
        return postRepository.save(post);
    }

    public Post updatePost(Long id, CreatePostRequestDto dto){
        Post post = getPost(id);
        User user = userService.getUser(dto.userId());

        post.updatePost(user, dto.content(), dto.state());
        return postRepository.save(post);
    }

    public void likePost(LikeRequestDto dto) {
        System.out.println("🔍 Like 요청 - Post ID: " + dto.targetId()); // ✅ 디버깅 로그 추가

        Post post = postRepository.findById(dto.targetId())
                .orElseThrow(() -> new IllegalStateException("게시글이 존재하지 않습니다. ID: " + dto.targetId()));

        System.out.println("✅ 게시글 확인됨 - Post ID: " + post.getId());

        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(post, user)) {
            return;
        }

        post.like(user);
        likeRepository.like(post, user);
    }

    public void unlikePost(LikeRequestDto dto) {
        Post post = getPost(dto.targetId()); // targetId로 변경
        User user = userService.getUser(dto.userId());

        if (likeRepository.checkLike(post, user)) { // 인스턴스를 통한 호출
            post.unlike();
            likeRepository.unlike(post, user);
        }
    }
}
