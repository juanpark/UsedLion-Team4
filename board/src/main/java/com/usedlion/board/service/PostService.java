package com.usedlion.board.service;

import com.usedlion.board.entity.Post;
import com.usedlion.board.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public Post create(Post p) {
        return repo.save(p);
    }

    public Post get(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post " + id));
    }

    public List<Post> getAll() {
        return repo.findAll();
    }

    public Post update(Integer id, Post up) {
        Post p = get(id);
        p.setTitle(up.getTitle());
        p.setContent(up.getContent());
        p.setPrice(up.getPrice());
        p.setView(up.getView());
        p.setStatus(up.getStatus());
        return repo.save(p);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}

