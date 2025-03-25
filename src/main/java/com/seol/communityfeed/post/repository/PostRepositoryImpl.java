package com.seol.communityfeed.post.repository;

import com.seol.communityfeed.post.application.Interface.PostRepository;
import com.seol.communityfeed.post.domain.Post;
import com.seol.communityfeed.post.repository.entity.post.PostEntity;
import com.seol.communityfeed.post.repository.jpa.JpaPostRepository;
import com.seol.communityfeed.user.application.Interface.UserRepository;
import com.seol.communityfeed.user.domain.User;
import com.seol.communityfeed.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.seol.communityfeed.post.repository.entity.post.QPostEntity.postEntity;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JpaPostRepository jpaPostRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Post save(Post post) {
        Long userId = post.getAuthor().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("작성자 없음"));
        UserEntity authorEntity = new UserEntity(user);
        PostEntity postEntity = new PostEntity(post, authorEntity);

        if (postEntity.getId() != null && jpaPostRepository.existsById(postEntity.getId())) {
            jpaPostRepository.updatePostEntity(postEntity);
            return jpaPostRepository.findById(postEntity.getId())
                    .orElseThrow(() -> new IllegalStateException("업데이트 후 데이터가 없음"))
                    .toPost();
        }

        postEntity = jpaPostRepository.save(postEntity);
        return postEntity.toPost();
    }

    @Override
    public Post findById(Long id) {
        PostEntity postEntity = jpaPostRepository.findById(id).orElseThrow();
        return postEntity.toPost();
    }

    @Override
    public List<Post> findAll() {
        return jpaPostRepository.findAll().stream()
                .map(PostEntity::toPost)
                .collect(Collectors.toList());
    }
}