package com.usedlion.board.controller;

import com.usedlion.board.dto.PostRequestDto;
import com.usedlion.board.dto.PostResponseDto;
import com.usedlion.board.entity.Post;
import com.usedlion.board.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<PostResponseDto>> listAll() {
        return ResponseEntity.ok(svc.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.get(id));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@ModelAttribute PostRequestDto dto) throws IOException {
        return ResponseEntity.ok(svc.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Integer id, @ModelAttribute PostRequestDto dto) throws IOException {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Post post = svc.getRaw(id);
        byte[] image = post.getFile();
        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(image);
    }
}



