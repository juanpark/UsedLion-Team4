package com.usedlion.back.controller;

import com.usedlion.back.entity.Chat;
import com.usedlion.back.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String getChat() {
        return "chat";
    }

    @GetMapping("/chat/{postId}")
    public String getChat(@PathVariable Long postId, Chat chat) {
        System.out.println(postId);
        return "chat";
    }

    @GetMapping("/chat/{postId}/history")
    @ResponseBody
    public List<Chat> getChatHistory(@PathVariable Integer postId) {
        return chatService.getChatHistory(postId); // DB에서 메시지 가져옴
    }
}
