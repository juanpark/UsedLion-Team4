package com.usedlion.board.controller;

import com.usedlion.board.entity.Post;
import com.usedlion.board.entity.SaleStatus;
import com.usedlion.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class PostPageController {
    private final PostService svc;

    public PostPageController(PostService svc) {
        this.svc = svc;
    }

    // 🔹 게시글 목록
    @GetMapping("/")
    public String listPage(Model model) {
        List<Post> posts = svc.getAll();

        posts.forEach(post -> {
            if (post.getFile() != null) {
                String base64 = Base64.getEncoder().encodeToString(post.getFile());
                post.setImageBase64(base64);
            }
        });

        model.addAttribute("posts", posts);
        return "index";
    }

    // 🔹 게시글 상세보기
    @GetMapping("/posts/{id}")
    public String viewPage(@PathVariable Integer id, Model model) {
        Post post = svc.getEntity(id);

        if (post.getFile() != null) {
            String base64 = Base64.getEncoder().encodeToString(post.getFile());
            post.setImageBase64(base64);
        }

        model.addAttribute("post", post);
        return "view";
    }

    // 🔹 글쓰기 폼 (고정값 제거)
    @GetMapping("/posts/new")
    public String writePage(Model model) {
        Post post = new Post(); // 고정값 없이 빈 객체만 전달
        model.addAttribute("post", post);
        model.addAttribute("statuses", SaleStatus.values());
        return "form";
    }

    // 🔹 수정 폼
    @GetMapping("/posts/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        Post post = svc.getEntity(id);
        model.addAttribute("post", post);
        model.addAttribute("statuses", SaleStatus.values());
        return "form";
    }

    // 🔹 새 글 저장
    @PostMapping("/posts")
    public String submitPost(@ModelAttribute Post post,
                             @RequestParam(value = "fileUpload", required = false) MultipartFile file) throws IOException {

        System.out.println("✅ userId from form: " + post.getUserId());

        if (file != null && !file.isEmpty()) {
            post.setFile(file.getBytes());
        }

        svc.create(post);
        return "redirect:/";
    }

    // 🔹 글 수정 저장
    @PostMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Integer id,
                             @ModelAttribute Post post,
                             @RequestParam(value = "fileUpload", required = false) MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            post.setFile(file.getBytes());
        }

        svc.update(id, post);
        return "redirect:/posts/" + id;
    }

    // 🔹 글 삭제
    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        svc.delete(id);
        return "redirect:/";
    }
}







