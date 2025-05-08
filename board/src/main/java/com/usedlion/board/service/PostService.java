package com.usedlion.board.service;

import com.usedlion.board.entity.Post;
import com.usedlion.board.entity.SaleStatus;
import com.usedlion.board.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    // 게시글 전체 조회
    public List<Post> getAll() {
        return repo.findAll();
    }

    // 게시글 생성
    public Post create(Post post) {
        return repo.save(post);
    }

    // 게시글 단건 조회
    public Post getEntity(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post " + id));
    }

    // 게시글 수정
    public Post update(Integer id, Post updatedPost) {
        Post post = getEntity(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setPrice(updatedPost.getPrice());
        post.setFile(updatedPost.getFile());
        post.setStatus(updatedPost.getStatus());
        return repo.save(post);
    }

    // 게시글 삭제
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}

