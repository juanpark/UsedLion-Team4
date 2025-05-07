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
public class ReplyDto {

    private Integer replyId;
    private Integer Start;
    private Integer profileId;
    private Integer ref;
    private Integer level;
    private Integer postId;
    private String content;
    private LocalDateTime date;

}
