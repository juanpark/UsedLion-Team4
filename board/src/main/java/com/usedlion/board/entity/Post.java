package com.usedlion.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId = 1;

    @Column(name = "profile_id")
    private Integer profileId = 1;

    @Column
    private Integer view = 0;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] file;

    private String title;

    private Integer price;

    private String content;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    private Boolean complete = false;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ONSALE','RESERVED','SOLDOUT')")
    private SaleStatus status;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer likes = 0;

    @Transient
    private String imageBase64;
}


