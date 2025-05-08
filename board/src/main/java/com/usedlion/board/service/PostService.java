package com.usedlion.board.service;

import com.usedlion.board.entity.Post;
import com.usedlion.board.entity.SaleStatus;
import com.usedlion.board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public Post create(Integer userId, Integer profileId, Integer view, String title,
                       Integer price, String content, String status, MultipartFile file) throws IOException {
        Post post = Post.builder()
                .userId(userId)
                .profileId(profileId)
                .view(view)
                .title(title)
                .price(price)
                .content(content)
                .status(SaleStatus.valueOf(status))
                .file(file != null ? file.getBytes() : null)
                .build();

        return repo.save(post);
    }

    public Post get(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post " + id));
    }

    public List<Post> getAll() {
        return repo.findAll();
    }

    public Post update(Integer id, Integer userId, Integer profileId, Integer view, String title,
                       Integer price, String content, String status, MultipartFile file) throws IOException {
        Post p = get(id);
        p.setUserId(userId);
        p.setProfileId(profileId);
        p.setView(view);
        p.setTitle(title);
        p.setPrice(price);
        p.setContent(content);
        p.setStatus(SaleStatus.valueOf(status));
        if (file != null && !file.isEmpty()) {
            p.setFile(file.getBytes());
        }
        return repo.save(p);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}

