package com.example.kyu.repository;

import com.example.kyu.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<LikePost, Integer> {

    void deleteByProfileIdAndPostId(Integer profileId, Integer postId);
}
