package com.usedlion.board.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "view")
    private Integer view;

    @Lob
    @Basic(fetch = FetchType.LAZY)
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

    @Column(name = "profile_id")
    private Integer profileId;

    // ✅ 여기 추가: 기본값이 0인 좋아요 수
    @Builder.Default
    @Column(name = "likes")
    private Integer likes = 0;
}
