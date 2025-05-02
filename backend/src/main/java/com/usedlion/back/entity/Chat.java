package com.usedlion.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer postId;

    private Integer userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    public Chat(Integer postId, Integer userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void setContent(String content) {this.content = content;}

    public int getMsgId() {return Id;}

    public int getPostId() {return postId;}

    public int getUserId() {return userId;}

    public String getContent() {return content;}

    public LocalDateTime getCreatedAt() {return createdAt;}
}
