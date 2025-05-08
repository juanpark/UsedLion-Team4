package com.example.post.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.example.post.dto.PostImage;
import com.example.post.dto.ReplyDetailDto;
import com.example.post.dto.ReplyTree;
import com.example.post.dto.SaleStatus;
import com.example.post.entity.Image;
import com.example.post.entity.Post;
import com.example.post.service.ImageService;
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
    private final ImageService imageService;

    public PostController(PostService postService, ReplyService replyService, UserService userService,
            ImageService imageService) {
        this.userService = userService;
        this.replyService = replyService;
        this.postService = postService;
        this.imageService = imageService;
    }

    @GetMapping("/{postId}")
    public String getPostDetail(@PathVariable Integer postId, Model model) {
        PostDetailDto postDetailDto = postService.getPostDetailByPostId(postId);
        List<ReplyDetailDto> replyDto = replyService.getReplyByPostId(postId);
        List<ReplyTree> replyTree = replyService.MakeTree(replyDto);
        List<Image> postImages = imageService.getImagesByPostId(postId);
        List<String> imageList = new ArrayList<>();
        for (Image image : postImages) {
            String base64Image = Base64.getEncoder().encodeToString(image.getFile());
            imageList.add(base64Image);
        }
        String base64File = postDetailDto.getFile() != null
                ? Base64.getEncoder().encodeToString(postDetailDto.getFile())
                : null;

        int viewCount = postDetailDto.getView();
        postDetailDto.setView(viewCount + 1);
        Post post = postService.getPostByPostId(postId);
        post.setView(postDetailDto.getView() + 1);
        postService.updatePost(postId, post);
        model.addAttribute("post", postDetailDto);

        model.addAttribute("replyTree", replyTree);
        model.addAttribute("imageList", imageList);

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
            @RequestParam("file") List<MultipartFile> files,
            Principal principal) throws IOException {

        String username = principal.getName(); // 로그인한 사용자의 ID 또는 username

        Post post = new Post();

        post.setTitle(title);
        post.setPrice(price);
        post.setContent(content);
        post.setStatus(status);
        post.setDate(LocalDateTime.now());
        post.setView(0);
        post.setUserId(userService.getUserByUsername(username).getUserId()); // 사용자 정보에서 userId 설정

        postService.createPost(post);
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                imageService.uploadImage(post.getPostId(), file);
            }
        }
        return "redirect:/post/" + post.getPostId(); // 생성된 게시물의 상세 페이지로 리다이렉트
    }

    // @GetMapping("/search")
    // public String searchPosts(Model model) {
    // return "searchPost";
    // }

    @GetMapping("/search/detail")
    public String searchDetail(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "content", required = false) String content,
            Principal principal, Model model) {
        String filter = null;
        String condition = null;
        List<PostDetailDto> posts = postService.searchPosts(title, username, content);
        List<PostImage> postImages = postService.makePostImage(posts);
        if (title != null && !title.isEmpty()) {
            filter = "제목";
            condition = title;
        } else if (username != null && !username.isEmpty()) {
            filter = "닉네임";
            condition = username;
        } else if (content != null && !content.isEmpty()) {
            filter = "내용";
            condition = content;
        }

        model.addAttribute("posts", postImages);
        model.addAttribute("username", principal.getName());
        model.addAttribute("filter", filter);
        model.addAttribute("condition", condition);

        return "hello";
    }

}
