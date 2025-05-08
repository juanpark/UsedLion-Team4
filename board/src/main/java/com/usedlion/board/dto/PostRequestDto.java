package com.usedlion.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostRequestDto {
    private Integer userId;
    private Integer profileId;
    private Integer view;
    private String title;
    private Integer price;
    private String content;
    private String status;
    private MultipartFile file;
}
