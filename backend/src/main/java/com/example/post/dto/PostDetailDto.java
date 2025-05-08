package com.example.post.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDetailDto {

    private Integer postId;
    private Integer userId;
    private Integer view;
    private byte[] file;
    private String title;
    private Integer price;
    private String content;
    private LocalDateTime created_at;
    private SaleStatus status;
    private String username;

}
