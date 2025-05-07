package com.example.post.dto;

public class PostImage {
    private PostDetailDto post;
    private String img;

    public PostImage(PostDetailDto post, String img) {
        this.post = post;
        this.img = img;
    }

    public PostDetailDto getPost() {
        return post;
    }

    public String getImg() {
        return img;
    }

}
