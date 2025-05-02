package com.example.reply.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Integer replyId;

    @Column(name = "profile_id")
    private Integer profileId;

    @Column(name = "ref")
    private Integer ref;

    @Column(name = "level")
    private Integer level;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private LocalDateTime date;
}
