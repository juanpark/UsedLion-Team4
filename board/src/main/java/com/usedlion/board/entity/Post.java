package com.usedlion.board.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    // ─── 이 부분을 post_id 로 매핑 ───────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;
    // ────────────────────────────────────────────────────────────

    // ─── profileId 역시 profile_id 로 매핑 (DB 스키마에 따라) ───────
    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    // ────────────────────────────────────────────────────────────

    private Integer view;

    @Lob
    private byte[] file;

    private String title;
    private Integer price;
    private String content;

    @Column(
            name = "date",
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ONSALE','RESERVED','SOLDOUT')")
    private SaleStatus status;
}

