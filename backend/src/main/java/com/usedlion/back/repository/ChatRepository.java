package com.usedlion.back.repository;

import com.usedlion.back.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    /**
     * 특정 게시글의 모든 채팅 메시지 조회
     */
    List<Chat> findByPostIdOrderByIdDesc(Integer postId);

    /**
     * 특정 게시글의 채팅 내역 페이징 조회
     */
    Page<Chat> findByPostIdOrderByIdDesc(Integer postId, Pageable pageable);

}