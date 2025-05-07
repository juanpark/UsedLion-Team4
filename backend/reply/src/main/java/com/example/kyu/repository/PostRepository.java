package com.example.kyu.repository;

import java.util.List;

import com.example.kyu.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.kyu.dto.PostDetailDto;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByProfileId(Integer profileId);

    Post getPostByPostId(Integer postId);

    @Query("SELECT new com.example.kyu.dto.PostDetailDto(p.postId, p.profileId, p.view, p.file, p.title, p.price, p.content, p.date, p.status,f.username) from Post p Join UserInformation f ON p.profileId = f.profileId WHERE p.postId = ?1")
    PostDetailDto getPostDetailByPostId(Integer postId);

    @Query("SELECT new com.example.kyu.dto.PostDetailDto(p.postId, p.profileId, p.view, p.file, p.title, p.price, p.content, p.date, p.status,f.username) from Post p Join UserInformation f ON p.profileId = f.profileId")
    List<PostDetailDto> getAllPostDetail();

}
