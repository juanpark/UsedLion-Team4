package com.example.post.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.post.dto.PostDetailDto;
import com.example.post.dto.PostImage;
import com.example.post.dto.ReplyDetailDto;
import com.example.post.entity.Report;
import com.example.post.entity.UserInformation;
import com.example.post.entity.UserPostLike;
import com.example.post.service.ImageService;
import com.example.post.service.PostService;
import com.example.post.service.ReplyService;
import com.example.post.service.ReportService;
import com.example.post.service.UserPostLikeService;
import com.example.post.service.UserService;

@Controller
@CrossOrigin
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final UserService userService;
    private final ReportService reportService;
    private final ReplyService replyService;
    private final ImageService imageService;
    private final UserPostLikeService userPostLikeService;

    public UserController(PostService postService, UserService userService,
            ReportService reportService,
            ReplyService replyService, ImageService imageService, UserPostLikeService userPostLikeService) {
        this.replyService = replyService;
        this.reportService = reportService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.postService = postService;
        this.userService = userService;
        this.imageService = imageService;
        this.userPostLikeService = userPostLikeService;
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserInformation user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserInformation user, Model model) {
        UserInformation foundUser = userService.getUserByUsername(user.getUsername());

        if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return "hello";
        } else {
            model.addAttribute("error", "잘못된 아이디 또는 비밀번호입니다.");
        }
        return "login";
    }

    @GetMapping("/hello")
    public String hello(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
        }
        List<PostDetailDto> posts = postService.getAllPostDetail();
        List<PostImage> postImages = postService.makePostImage(posts);
        model.addAttribute("posts", postImages);
        return "hello";
    }

    @GetMapping("/user/{userId}")
    public String getUser(@PathVariable Integer userId, Model model, Principal principal) {
        UserInformation user = userService.getUserById(userId);

        List<PostDetailDto> posts = postService.searchPosts(null, user.getUsername(), null);
        List<PostImage> postImages = postService.makePostImage(posts);
        List<Report> reports = reportService.getByuserId(userId);
        List<ReplyDetailDto> replies = replyService.getReplyByUserId(userId);
        List<UserPostLike> likes = userPostLikeService.getUserPostLikeByUserId(userId);

        List<PostDetailDto> likePosts = new ArrayList<>();

        for (UserPostLike like : likes) {
            PostDetailDto post = postService.getPostDetailByPostId(like.getPostId());
            likePosts.add(post);
        }
        List<PostImage> likePostImages = postService.makePostImage(likePosts);

        model.addAttribute("user", user);
        model.addAttribute("posts", postImages);
        model.addAttribute("postCount", posts.size());
        model.addAttribute("reportCount", reports.size());
        model.addAttribute("reports", reports);
        model.addAttribute("replies", replies);
        model.addAttribute("replyCount", replies.size());
        model.addAttribute("likes", likePostImages);
        model.addAttribute("likeCount", likePosts.size()); // Updated to use likePosts.size()
        return "userDetail";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/report/{postId}/{userId}")
    public String reportPost(@PathVariable Integer postId, @PathVariable Integer userId,
            @RequestParam("reason") String reason,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        UserInformation user = userService.getUserByUsername(username);
        Report report = new Report();
        report.setReporterId(user.getUserId());
        report.setTargetId(userId);
        report.setContent(reason);

        reportService.createReport(report);
        return "redirect:/post/" + postId; // Redirect to the post detail page after reporting
    }

}
