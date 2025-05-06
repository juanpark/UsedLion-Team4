package com.example.usedlion.controller;

import com.example.usedlion.entity.ChatMessage;
import com.example.usedlion.repository.ChatMessageRepository;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;  
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Controller
public class ChatController {

    private final ChatMessageRepository repo;
    private final SimpMessagingTemplate messagingTemplate;
    private final AtomicInteger userCount = new AtomicInteger();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");


    public ChatController(ChatMessageRepository repo,
                          SimpMessagingTemplate messagingTemplate) {
        this.repo = repo;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")    // 클라이언트 발송 /app/chat.sendMessage
    @SendTo("/topic/public")                // 서버 방송 /topic/public
    public ChatMessage sendMessage(ChatMessage msg) {
        // timestamp 세팅
        msg.setTimestamp(LocalDateTime.now().format(fmt));
        // DB 저장
        repo.save(msg);
        return msg;
    }

    // 과거 대화 내역 REST 조회 (/chat/history)
    @GetMapping("/chat/history")
    @ResponseBody
    public List<ChatMessage> getChatHistory() {
        return repo.findAll();
    }


    // WebSocket 연결 이벤트 - 사용자 수 증가
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        int count = userCount.incrementAndGet();
        messagingTemplate.convertAndSend("/topic/userCount", count);
    }

    // WebSocket 연결 해제 이벤트 - 사용자 수 감소
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        int count = userCount.decrementAndGet();
        messagingTemplate.convertAndSend("/topic/userCount", count);
    }

    // 현재 접속자 수 REST 조회 (/chat/userCount)
    @GetMapping("/chat/userCount")
    @ResponseBody
    public int getCurrentUserCount() {
        return userCount.get();
    }
}