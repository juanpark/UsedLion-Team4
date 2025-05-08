package com.example.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.entity.LikePost;

public interface PostLikeRepository extends JpaRepository<LikePost, Integer> {

    void deleteByUserIdAndPostId(Integer userId, Integer postId);
}
