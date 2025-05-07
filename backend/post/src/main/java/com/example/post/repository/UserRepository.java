package com.example.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.entity.UserInformation;

public interface UserRepository extends JpaRepository<UserInformation, Integer> {

    UserInformation findByUsername(String nickname);
}
