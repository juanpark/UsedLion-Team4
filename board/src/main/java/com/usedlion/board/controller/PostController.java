package com.usedlion.board.controller;

import com.usedlion.board.entity.Post;
import com.usedlion.board.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService svc;

    public PostController(PostService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Post> listAll() {
        return svc.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.get(id));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Post post = svc.get(id);
        byte[] image = post.getFile();
        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(image);
    }

    @PostMapping
    public ResponseEntity<Post> create(
            @RequestParam("userId") Integer userId,
            @RequestParam("profileId") Integer profileId,
            @RequestParam("view") Integer view,
            @RequestParam("title") String title,
            @RequestParam("price") Integer price,
            @RequestParam("content") String content,
            @RequestParam("status") String status,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(svc.create(userId, profileId, view, title, price, content, status, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(
            @PathVariable Integer id,
            @RequestParam("userId") Integer userId,
            @RequestParam("profileId") Integer profileId,
            @RequestParam("view") Integer view,
            @RequestParam("title") String title,
            @RequestParam("price") Integer price,
            @RequestParam("content") String content,
            @RequestParam("status") String status,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(svc.update(id, userId, profileId, view, title, price, content, status, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}


