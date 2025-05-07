package com.example.kyu.service;

import com.example.kyu.entity.UserInformation;
import org.springframework.stereotype.Service;

import com.example.kyu.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInformation getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserInformation getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

}
