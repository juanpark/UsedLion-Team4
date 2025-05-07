package com.usedlion.board.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // DB 컬럼명에 맞춤
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "view")
    private Integer view;

    @Lob
    private byte[] file;

    private String title;

    private Integer price;

    private String content;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "complete")
    private Boolean complete;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ONSALE','RESERVED','SOLDOUT')")
    private SaleStatus status;

    // profile_id 컬럼도 존재하므로 여기에 포함
    @Column(name = "profile_id")
    private Integer profileId;
}

