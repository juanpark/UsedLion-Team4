package com.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContainingOrContentContaining(String title, String content);
}