package com.example.kyu.controller;

import com.example.kyu.dto.ReplyDTO;
import com.example.kyu.entity.Post;
import com.example.kyu.service.PostService;
import com.example.kyu.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kyu.dto.PostDetailDto;

import java.util.List;

@Controller
@RequestMapping("/post")
@CrossOrigin
public class PostController {
    private final PostService postService;
    private final ReplyService replyService;  // ← 추가

    public PostController(PostService postService,
                          ReplyService replyService) {
        this.postService = postService;
        this.replyService = replyService;
    }

    @GetMapping("/{postId}")
    public String getPostDetail(@PathVariable Integer postId, Model model) {
        PostDetailDto postDetail = postService.getPostDetailByPostId(postId);

        List<ReplyDTO> replies = replyService.findByPostId(postId);
        ReplyDTO newReply = new ReplyDTO();
        newReply.setPostId(postId);

        model.addAttribute("post", postDetail);
        model.addAttribute("replies", replies);
        model.addAttribute("newReply", newReply);

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
