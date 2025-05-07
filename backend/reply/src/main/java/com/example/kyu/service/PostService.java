package com.example.kyu.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.kyu.entity.Post;
import org.springframework.stereotype.Service;

import com.example.kyu.dto.PostDetailDto;
import com.example.kyu.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getPostById(Integer profileId) {
        return postRepository.findByProfileId(profileId);
    }

    public Post getPostByPostId(Integer postId) {
        return postRepository.getPostByPostId(postId);
    }

    public PostDetailDto getPostDetailByPostId(Integer postId) {
        return postRepository.getPostDetailByPostId(postId);
    }

    public Post updatePost(Integer id, Post post) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost == null) {
            return null;
        }
        if (post.getPostId() != null) {
            existingPost.setPostId(post.getPostId());
        }
        if (post.getPrice() != null) {
            existingPost.setPrice(post.getPrice());
        }
        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }
        if (post.getContent() != null) {
            existingPost.setContent(post.getContent());
        }
        if (post.getDate() != null) {
            existingPost.setDate(post.getDate());
        }
        if (post.getProfileId() != null) {
            existingPost.setProfileId(post.getProfileId());
        }
        return postRepository.save(existingPost);
    }

    public List<PostDetailDto> getAllPostDetail() {
        return postRepository.getAllPostDetail();
    }

    public List<Post> getPostsByProfileId(Integer profileId) {
        return postRepository.findAll().stream()
                .filter(post -> post.getProfileId().equals(profileId))
                .collect(Collectors.toList());
    }

}
