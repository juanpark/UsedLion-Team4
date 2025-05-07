package com.example.post.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.post.entity.Image;
import com.example.post.repository.ImageRepository;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void uploadImage(Integer postId, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setPostId(postId);
        image.setFile(file.getBytes());

        imageRepository.save(image);
    }

    public List<Image> getImagesByPostId(Integer postId) {
        return imageRepository.findByPostId(postId);
    }

}
