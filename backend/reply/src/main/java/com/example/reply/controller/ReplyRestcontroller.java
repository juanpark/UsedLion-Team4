package com.example.reply.controller;

import com.example.reply.dto.ReplyDTO;
import com.example.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                                 // JSON 응답
@RequestMapping("/api/replies")                 // 공통 URL prefix
@RequiredArgsConstructor
public class ReplyRestcontroller {

    private final ReplyService svc;

    /** 1) 특정 게시글 댓글 조회 **/
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReplyDTO>> getByPost(
            @PathVariable Integer postId) {
        return ResponseEntity.ok(svc.findByPostId(postId));
    }

    /** 2) 댓글 작성 **/
    @PostMapping
    public ResponseEntity<ReplyDTO> create(
            @RequestBody ReplyDTO replyDto) {
        return ResponseEntity.ok(svc.create(replyDto));
    }

    /** 3) 댓글 수정 **/
    @PutMapping("/{id}")
    public ResponseEntity<ReplyDTO> update(
            @PathVariable("id") Integer replyId,
            @RequestBody ReplyDTO replyDto) {
        return ResponseEntity.ok(svc.update(replyId, replyDto));
    }

    /** 4) 댓글 삭제 **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer replyId) {
        svc.delete(replyId);
        return ResponseEntity.noContent().build();
    }
}
