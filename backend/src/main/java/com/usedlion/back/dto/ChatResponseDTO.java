package com.usedlion.back.dto;

import com.usedlion.back.entity.Chat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatResponseDTO {
    private int id;
    private int userId;
    private String content;
    private String userName;
    private LocalDateTime createdAt ;

    public ChatResponseDTO(Chat chat /*User user*/) {
        this.id = chat.getMsgId();
        this.userId = chat.getUserId();
        this.content = chat.getContent();
//        this.userName = User.getUserNickName();
        this.createdAt = chat.getCreatedAt();
    }

}
