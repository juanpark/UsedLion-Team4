package com.usedlion.board.service;

import com.usedlion.board.dto.PostRequestDto;
import com.usedlion.board.dto.PostResponseDto;
import com.usedlion.board.entity.Post;
import com.usedlion.board.entity.SaleStatus;
import com.usedlion.board.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public PostResponseDto create(PostRequestDto dto) throws IOException {
        Post post = Post.builder()
                .userId(dto.getUserId())
                .profileId(dto.getProfileId())
                .view(dto.getView())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .content(dto.getContent())
                .status(SaleStatus.valueOf(dto.getStatus()))
                .file(dto.getFile() != null ? dto.getFile().getBytes() : null)
                .build();
        return toResponseDto(repo.save(post));
    }

    public PostResponseDto update(Integer id, PostRequestDto dto) throws IOException {
        Post p = getRaw(id);
        p.setUserId(dto.getUserId());
        p.setProfileId(dto.getProfileId());
        p.setView(dto.getView());
        p.setTitle(dto.getTitle());
        p.setPrice(dto.getPrice());
        p.setContent(dto.getContent());
        p.setStatus(SaleStatus.valueOf(dto.getStatus()));
        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            p.setFile(dto.getFile().getBytes());
        }
        return toResponseDto(repo.save(p));
    }

    public List<PostResponseDto> getAll() {
        return repo.findAll().stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    public PostResponseDto get(Integer id) {
        return toResponseDto(getRaw(id));
    }

    public Post getRaw(Integer id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No post " + id));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private PostResponseDto toResponseDto(Post p) {
        return PostResponseDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .content(p.getContent())
                .price(p.getPrice())
                .view(p.getView())
                .userId(p.getUserId())
                .profileId(p.getProfileId())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .imageUrl("http://localhost:8080/posts/" + p.getId() + "/image")
                .build();
    }
}


