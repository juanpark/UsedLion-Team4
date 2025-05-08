package com.post.controller;

import org.springframework.web.bind.annotation.*;
import com.post.entity.Post;
import com.post.service.PostService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/{id}/like")
    public Post likePost(@PathVariable("id") Long id) {
        return postService.likePost(id);
    }

    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam("keyword") String keyword) {
        return postService.searchPosts(keyword);
    }
}