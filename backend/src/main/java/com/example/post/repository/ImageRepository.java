package com.example.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    public List<Image> findByPostId(Integer postId);
}
