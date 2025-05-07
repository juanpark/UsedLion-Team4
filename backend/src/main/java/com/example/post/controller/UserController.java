package com.example.post.controller;

import java.security.Principal;
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
import com.example.post.entity.Post;
import com.example.post.entity.Report;
import com.example.post.entity.UserInformation;
import com.example.post.repository.ReportRepository;
import com.example.post.repository.UserRepository;
import com.example.post.service.PostService;
import com.example.post.service.UserService;

@Controller
@CrossOrigin
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final UserService userService;
    private final ReportRepository reportRepository;

    public UserController(UserRepository userRepository, PostService postService, UserService userService,
            ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.postService = postService;
        this.userService = userService;
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
        userRepository.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserInformation user, Model model) {
        UserInformation foundUser = userRepository.findByUsername(user.getUsername());

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
        model.addAttribute("posts", posts);
        return "hello";
    }

    @GetMapping("/user/{profileId}")
    public String getUser(@PathVariable Integer profileId, Model model) {
        UserInformation user = userService.getUserById(profileId);
        List<Post> posts = postService.getPostsByProfileId(profileId);
        List<Report> reports = reportRepository.findByTargetId(profileId);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("reportCount", reports.size());
        model.addAttribute("reports", reports);
        return "userDetail";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/report/{postId}/{profileId}")
    public String reportPost(@PathVariable Integer postId, @PathVariable Integer profileId,
            @RequestParam("reason") String reason,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        UserInformation user = userRepository.findByUsername(username);
        Report report = new Report();
        report.setProfileId(user.getProfileId());
        report.setTargetId(profileId);
        report.setContent(reason);

        reportRepository.save(report);
        return "redirect:/post/" + postId; // Redirect to the post detail page after reporting
    }

}
