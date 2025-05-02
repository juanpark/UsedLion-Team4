package com.usedlion.back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usedlion.back.entity.Chat;
import com.usedlion.back.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
@RequiredArgsConstructor
public class ChatService extends TextWebSocketHandler {

    // postId별로 웹소켓 세션 그룹화 (채팅방)
    private final Map<Integer, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();

    // 세션 ID와 postId 매핑
    private final Map<String, Integer> sessionPostIdMap = new ConcurrentHashMap<>();

    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;

    /**
     * 클라이언트 연결 성립 시 호출되는 메서드
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("새로운 WebSocket 연결: {}", session.getId());

        // URI에서 postId 추출
        Integer postId = extractPostId(session);
        log.info("채팅방 입장: postId={}, sessionId={}", postId, session.getId());

        // 채팅방에 세션 추가
        joinChatRoom(session, postId);

        // 입장 메시지 브로드캐스팅
        Map<String, Object> message = new HashMap<>();
        message.put("type", "JOIN");
        message.put("postId", postId);
        message.put("sessionId", session.getId());
        message.put("message", "새로운 사용자가 입장했습니다.");
        message.put("timestamp", new Date());

        broadcastToRoom(postId, new TextMessage(objectMapper.writeValueAsString(message)), session );
    }

    /**
     * 클라이언트가 메시지를 전송할 때 호출되는 메서드
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();
        log.info("메시지 수신: {}", payload);

        try {
            // JSON 메시지 파싱
            Map<String, Object> messageMap = objectMapper.readValue(payload, Map.class);

            // 필수 필드 확인
            if (!messageMap.containsKey("postId") || !messageMap.containsKey("userId") || !messageMap.containsKey("content")) {
                session.sendMessage(new TextMessage("{\"error\": \"필수 필드가 누락되었습니다. (postId, userId, content)\"}"));
                return;
            }

            Integer postId = Integer.parseInt(messageMap.get("postId").toString());
            Integer userId = Integer.parseInt(messageMap.get("userId").toString());
            String content = messageMap.get("content").toString();

            // 채팅 메시지 객체 생성
            Chat Chat = new Chat(postId,userId,content);


            // DB에 메시지 저장
            Chat savedMessage = chatRepository.save(Chat);

            // 메시지 브로드캐스팅을 위한 데이터 준비
            Map<String, Object> broadcastMessage = new HashMap<>();
            broadcastMessage.put("type", "MESSAGE");
            broadcastMessage.put("id", savedMessage.getMsgId());
            broadcastMessage.put("postId", savedMessage.getPostId());
            broadcastMessage.put("senderId", savedMessage.getUserId());
            broadcastMessage.put("content", savedMessage.getContent());
            broadcastMessage.put("timestamp", savedMessage.getCreatedAt());

            // 채팅방에 메시지 브로드캐스팅
            broadcastToRoom(postId, new TextMessage(objectMapper.writeValueAsString(broadcastMessage)), session);

        } catch (Exception e) {
            log.error("메시지 처리 중 오류 발생", e);
            session.sendMessage(new TextMessage("{\"error\": \"메시지 처리 중 오류가 발생했습니다: " + e.getMessage() + "\"}"));
        }
    }

    /**
     * 클라이언트 연결 종료 시 호출되는 메서드
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket 연결 종료: {}, 상태: {}", session.getId(), status);

        // 세션이 속한 채팅방 찾기
        Integer postId = sessionPostIdMap.get(session.getId());
        if (postId != null) {
            // 채팅방에서 세션 제거
            leaveChatRoom(session, postId);

            // 퇴장 메시지 브로드캐스팅
            Map<String, Object> message = new HashMap<>();
            message.put("type", "LEAVE");
            message.put("postId", postId);
            message.put("sessionId", session.getId());
            message.put("message", "사용자가 퇴장했습니다.");
            message.put("timestamp", new Date());

            broadcastToRoom(postId, new TextMessage(objectMapper.writeValueAsString(message)), session);
        }
    }

    /**
     * 예외 발생 시 호출되는 메서드
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket 전송 오류: {}", session.getId(), exception);
    }

    /**
     * 채팅방 입장 처리
     */
    private void joinChatRoom(WebSocketSession session, Integer postId) {
        // 채팅방이 없으면 새로 생성
        chatRooms.computeIfAbsent(postId, k -> ConcurrentHashMap.newKeySet()).add(session);

        // 세션과 채팅방 매핑 저장
        sessionPostIdMap.put(session.getId(), postId);
    }

    /**
     * 채팅방 퇴장 처리
     */
    private void leaveChatRoom(WebSocketSession session, Integer postId) {
        Set<WebSocketSession> chatRoom = chatRooms.get(postId);
        if (chatRoom != null) {
            chatRoom.remove(session);

            // 채팅방이 비어있으면 제거
            if (chatRoom.isEmpty()) {
                chatRooms.remove(postId);
            }
        }

        // 세션 매핑 제거
        sessionPostIdMap.remove(session.getId());
    }

    /**
     * 특정 채팅방에 메시지 브로드캐스팅
     */
    private void broadcastToRoom(Integer postId, TextMessage message,  WebSocketSession senderSession) {
        Set<WebSocketSession> sessions = chatRooms.get(postId);
        if (sessions != null) {
            sessions.forEach(session -> {
                try {
                    // 자기 자신을 제외한 세션에만 메시지 전송
                    if (session.isOpen() && !session.getId().equals(senderSession.getId())) {
                        session.sendMessage(message);
                    }
                } catch (IOException e) {
                    log.error("메시지 전송 실패: {}", session.getId(), e);
                }
            });
        }
    }

    /**
     * URI에서 postId 추출
     */
    private Integer extractPostId(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        // /ws/chat/{postId} 형식에서 postId 추출
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("chat") && i + 1 < parts.length) {
                return Integer.parseInt(parts[i + 1]);
            }
        }
        throw new IllegalArgumentException("잘못된 URL 형식입니다");
    }

    public List<Chat> getChatHistory(Integer postId) {
        // postId가 null이거나 유효하지 않은 값일 수 있으므로 예외 처리를 추가
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 postId 값입니다.");
        }
        return chatRepository.findByPostIdOrderByCreatedAtAsc(postId);  // DB 조회
    }
}