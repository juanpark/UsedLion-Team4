package com.usedlion.board.controller;

import com.usedlion.board.entity.Post;
import com.usedlion.board.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post p) {
        return ResponseEntity.ok(svc.create(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(
            @PathVariable Integer id,
            @RequestBody Post p
    ) {
        return ResponseEntity.ok(svc.update(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
