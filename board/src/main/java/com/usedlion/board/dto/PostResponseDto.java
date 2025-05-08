package com.usedlion.board.dto;

import com.usedlion.board.entity.SaleStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Integer id;
    private String title;
    private String content;
    private Integer price;
    private Integer view;
    private Integer userId;
    private Integer profileId;
    private SaleStatus status;
    private LocalDateTime createdAt;
    private String imageUrl;  // 프론트에서 <img src=...> 로 사용 가능
}

