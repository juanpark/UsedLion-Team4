package com.example.post.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.post.dto.PostDetailDto;
import com.example.post.dto.ReplyDetailDto;
import com.example.post.dto.ReplyTree;
import com.example.post.dto.SaleStatus;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import com.example.post.service.ReplyService;
import com.example.post.service.UserService;

@Controller
@RequestMapping("/post")
@CrossOrigin
public class PostController {
    private final PostService postService;
    private final ReplyService replyService;
    private final UserService userService;

    public PostController(PostService postService, ReplyService replyService, UserService userService) {
        this.userService = userService;
        this.replyService = replyService;
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public String getPostDetail(@PathVariable Integer postId, Model model) {
        PostDetailDto postDetailDto = postService.getPostDetailByPostId(postId);
        List<ReplyDetailDto> replyDto = replyService.getReplyByPostId(postId);
        List<ReplyTree> replyTree = replyService.MakeTree(replyDto);
        String base64File = postDetailDto.getFile() != null
                ? Base64.getEncoder().encodeToString(postDetailDto.getFile())
                : null;

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
        model.addAttribute("base64File", base64File);

        return "postDetail";
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Integer id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        return "createPost";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam("title") String title,
            @RequestParam("price") Integer price,
            @RequestParam("content") String content,
            @RequestParam("status") SaleStatus status,
            @RequestParam("file") MultipartFile file,
            Principal principal) throws IOException {

        String username = principal.getName(); // 로그인한 사용자의 ID 또는 username

        Post post = new Post();
        post.setTitle(title);
        post.setPrice(price);
        post.setContent(content);
        post.setStatus(status);
        post.setDate(LocalDateTime.now());
        post.setView(0);
        post.setFile(file.isEmpty() ? null : file.getBytes());
        post.setProfileId(userService.getUserByUsername(username).getProfileId()); // 사용자 정보에서 profileId 설정

        postService.createPost(post);
        return "redirect:/post/" + post.getPostId(); // 생성된 게시물의 상세 페이지로 리다이렉트
    }

}
