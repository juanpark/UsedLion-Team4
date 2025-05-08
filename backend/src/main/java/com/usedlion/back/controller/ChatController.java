package com.usedlion.back.controller;

import com.usedlion.back.dto.ChatResponseDTO;
import com.usedlion.back.entity.Chat;
import com.usedlion.back.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;

    @GetMapping("/chat")
    public String getChat() {
        return "chat";
    }

    /**
     * 채팅방 입장
     */
    @GetMapping("/chat/{postId}")
    public String getChat(@PathVariable Long postId, Chat chat) {
        return "chat";
    }

    /**
     * 이전 메세지 전체 조회
     */
    @GetMapping("/chat/{postId}/history")
    @ResponseBody
    public List<Chat> getChatHistory(@PathVariable Integer postId) {
        return chatService.getChatHistory(postId); // DB에서 메시지 가져옴
    }

    /**
     * 채팅 내역 페이징 조회
     */
    @GetMapping("/chat/{postId}/page")
    public ResponseEntity<Page<ChatResponseDTO>> getChatPage(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
            ){
        Page<ChatResponseDTO> chats = chatService.getChatPage(postId, page, size);
        return ResponseEntity.ok(chats);
    }
}
