package com.example.kyu.repository;

import com.example.kyu.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    List<Reply> findByPostId(Integer postId); // 해당 게시물에 달린 댓글 조회
}
