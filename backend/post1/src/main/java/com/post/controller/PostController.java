package com.post.controller;

import org.springframework.web.bind.annotation.*;
import com.post.entity.Post;
import com.post.service.PostService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @PostMapping("/posts")
    public String createPost(@RequestParam("title") String title,
                             @RequestParam("content") String content) {
        postService.createPost(new Post(title, content));
        return "redirect:/";
    }

    @GetMapping("/posts/search")
    public String searchPosts(@RequestParam("keyword") String keyword, Model model) {
        List<Post> result = postService.searchPosts(keyword);
        model.addAttribute("posts", result);
        return "index";
    }
    @PostMapping("/posts/{id}/like")
    public String toggleLike(@PathVariable("id") Long id, HttpSession session) {
        postService.toggleLike(id, session);
        return "redirect:/";
    }
}