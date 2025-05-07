package com.example.post.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ImageDto {

    private Integer imageId;
    private Integer postId;
    private byte[] file;

}
