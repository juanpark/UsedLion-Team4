package com.post.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.post.entity.Post;
import com.post.repository.PostRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }
    public void toggleLike(Long postId, HttpSession session) {
        String sessionKey = "liked_" + postId;
        Post post = postRepository.findById(postId).orElseThrow();

        // 이미 추천했으면 취소
        if (session.getAttribute(sessionKey) != null) {
            // 추천 취소
            post.setLikes(post.getLikes() - 1);
            session.removeAttribute(sessionKey);
        } else {
            // 추천 추가
            post.setLikes(post.getLikes() + 1);
            session.setAttribute(sessionKey, true);
        }

        postRepository.save(post);
    }
}