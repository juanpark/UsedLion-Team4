package com.usedlion.back.repository;

import com.usedlion.back.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    /**
     * 특정 게시글의 모든 채팅 메시지 조회
     */
    List<Chat> findByPostIdOrderByCreatedAtAsc(Integer postId);

    /**
     * 특정 사용자의 모든 채팅 메시지 조회
     */
    List<Chat> findByUserIdOrderByCreatedAtDesc(Integer userId);

    /**
     * 특정 게시글에서 특정 사용자의 채팅 메시지 조회
     */
    List<Chat> findByPostIdAndUserIdOrderByCreatedAtAsc(Integer postId, Integer userId);
}