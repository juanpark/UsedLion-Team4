package com.example.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.post.dto.PostDetailDto;
import com.example.post.entity.Post;
import com.example.post.service.PostService;

@Controller
@RequestMapping("/post")
@CrossOrigin
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public String getPostDetail(@PathVariable Integer postId, Model model) {
        PostDetailDto postDetailDto = postService.getPostDetailByPostId(postId);

        int viewCount = postDetailDto.getView();
        postDetailDto.setView(viewCount + 1);
        Post post = postService.getPostByPostId(postId);
        post.setView(postDetailDto.getView() + 1);
        postService.updatePost(postId, post);
        model.addAttribute("post", postDetailDto);

        return "postDetail";
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Integer id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @PostMapping("/create/{profileId}")
    public Post createPost(@PathVariable Integer profileId, @RequestBody Post post) {
        post.setProfileId(profileId);
        return postService.createPost(post);
    }

}
