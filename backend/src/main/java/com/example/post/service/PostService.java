package com.example.post.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.post.dto.PostDetailDto;
import com.example.post.dto.PostImage;
import com.example.post.entity.Post;
import com.example.post.repository.PostRepository;

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

    public List<PostDetailDto> searchPosts(String title, String username, String content) {
        if (title != null && !title.isEmpty()) {
            return postRepository.findByTitleContaining(title);
        } else if (username != null && !username.isEmpty()) {
            return postRepository.findByNicknameContaining(username);
        } else if (content != null && !content.isEmpty()) {
            return postRepository.findByContentContaining(content);

        }
        return postRepository.getAllPostDetail();
    }

    public List<PostImage> makePostImage(List<PostDetailDto> posts) {
        List<PostImage> postImages = new ArrayList<>();
        for (PostDetailDto post : posts) {
            System.out.println(post.getTitle());
            String base64File = null;
            if (post.getFile() != null) {
                base64File = Base64.getEncoder().encodeToString(post.getFile());
            }
            PostImage pImage = new PostImage(post, base64File);
            postImages.add(pImage);
        }
        return postImages;

    }

}
