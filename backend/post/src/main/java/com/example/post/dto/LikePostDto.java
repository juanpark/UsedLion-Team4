package com.example.post.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LikePostDto {

    private Integer id;
    private Integer profileId;
    private Integer postId;

}
