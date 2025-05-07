package com.example.kyu.service;

import com.example.kyu.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    ReplyDTO create(ReplyDTO dto);// 댓글 저장 후 DTO로 반환
    ReplyDTO update(Integer id, ReplyDTO dto); // 수정
    void delete(Integer id); // 삭제
    List<ReplyDTO> findByPostId(Integer postId);
    ReplyDTO findById(Integer replyId);
}
