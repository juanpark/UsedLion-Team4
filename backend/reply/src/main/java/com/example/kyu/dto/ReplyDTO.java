package com.example.kyu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replyId;
    private Integer profileId;
    private Integer ref;
    private Integer level;
    private Integer postId;
    private String content;
    private LocalDateTime date;

}
