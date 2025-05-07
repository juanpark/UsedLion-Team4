package com.example.kyu.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private Integer id;

    private Integer profileId;
    private Integer view;
    private byte[] file;
    private String title;
    private Integer price;
    private String content;
    private LocalDateTime date;
    private SaleStatus status;

}
