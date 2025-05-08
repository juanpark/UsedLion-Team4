package com.example.post.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer replyId;
    private Integer start;
    private Integer userId;
    private Integer ref;
    private Integer level;
    private Integer postId;
    private String content;
    private LocalDateTime date;

}
