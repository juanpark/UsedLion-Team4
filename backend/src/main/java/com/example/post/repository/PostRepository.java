package com.example.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.post.dto.PostDetailDto;
import com.example.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserId(Integer userId);

    Post getPostByPostId(Integer postId);

    @Query("SELECT new com.example.post.dto.PostDetailDto(p.postId, p.userId, p.view, p.file, p.title, p.price, p.content, p.date, p.status,f.username) from Post p Join UserInformation f ON p.userId = f.userId WHERE p.postId = ?1")
    PostDetailDto getPostDetailByPostId(Integer postId);

    @Query("SELECT new com.example.post.dto.PostDetailDto(p.postId, p.userId, p.view, p.file, p.title, p.price, p.content, p.date, p.status,f.username) from Post p Join UserInformation f ON p.userId = f.userId")
    List<PostDetailDto> getAllPostDetail();

    @Query("SELECT new com.example.post.dto.PostDetailDto(p.postId, p.userId, p.view, p.file, p.title, p.price, p.content, p.date, p.status,f.username) from Post p Join UserInformation f ON p.userId = f.userId WHERE p.title like %?1%")
    List<PostDetailDto> findByTitleContaining(String title);

    @Query("SELECT new com.example.post.dto.PostDetailDto(p.postId, p.userId, p.view, p.file, p.title, p.price, p.content, p.date, p.status,f.username) from Post p Join UserInformation f ON p.userId = f.userId WHERE f.username like %?1%")
    List<PostDetailDto> findByNicknameContaining(String username);

    @Query("SELECT new com.example.post.dto.PostDetailDto(p.postId, p.userId, p.view, p.file, p.title, p.price, p.content, p.date, p.status,f.username) from Post p Join UserInformation f ON p.userId = f.userId WHERE p.content like %?1%")
    List<PostDetailDto> findByContentContaining(String content);

}
