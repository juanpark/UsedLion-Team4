package com.example.post.service;

import org.springframework.stereotype.Service;

import com.example.post.entity.UserInformation;
import com.example.post.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserInformation user) {
        userRepository.save(user);
    }

    public UserInformation getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserInformation getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

}
