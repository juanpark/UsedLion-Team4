package com.usedlion.board.dto;

import com.usedlion.board.entity.Post;
import com.usedlion.board.entity.SaleStatus;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Integer id;
    private String title;
    private String content;
    private Integer price;
    private byte[] file;
    private SaleStatus status;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.file = post.getFile();
        this.status = post.getStatus();
    }
}

