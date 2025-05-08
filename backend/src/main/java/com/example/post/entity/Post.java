package com.example.post.entity;

import java.time.LocalDateTime;
import com.example.post.dto.SaleStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer postId;
    private Integer userId;
    private Integer view;
    @Lob
    private byte[] file;
    private String title;
    private Integer price;
    private String content;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ONSALE','RESERVED','SOLDOUT')")
    private SaleStatus status;

}
