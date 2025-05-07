package com.example.post.controller;

import java.util.List;

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
import com.example.post.dto.ReplyDetailDto;
import com.example.post.dto.ReplyTree;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import com.example.post.service.ReplyService;

@Controller
@RequestMapping("/post")
@CrossOrigin
public class PostController {
    private final PostService postService;
    private final ReplyService replyService;

    public PostController(PostService postService, ReplyService replyService) {
        this.replyService = replyService;
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public String getPostDetail(@PathVariable Integer postId, Model model) {
        PostDetailDto postDetailDto = postService.getPostDetailByPostId(postId);
        List<ReplyDetailDto> replyDto = replyService.getReplyByPostId(postId);
        List<ReplyTree> replyTree = replyService.MakeTree(replyDto);
        int viewCount = postDetailDto.getView();
        postDetailDto.setView(viewCount + 1);
        Post post = postService.getPostByPostId(postId);
        post.setView(postDetailDto.getView() + 1);
        postService.updatePost(postId, post);
        model.addAttribute("post", postDetailDto);
        for (ReplyTree r : replyTree) {
            System.out.println(r.getReply().getReplyId());
            System.out.println(r.getReply().getContent());

            if (r.getChildren() != null) {
                for (ReplyTree child : r.getChildren()) {
                    System.out.println(child.getReply().getReplyId());
                    System.out.println(child.getReply().getContent());
                }
            }
            System.out.println(r.getChildren().size());
        }
        model.addAttribute("replyTree", replyTree);

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
