package com.usedlion.board.service;

import com.usedlion.board.entity.Post;
import com.usedlion.board.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> get(Long id) {
        return postRepository.findById(id);
    }

    public Post update(Long id, Post updated) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
        post.setTitle(updated.getTitle());
        post.setContent(updated.getContent());
        post.setPrice(updated.getPrice());
        return postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
