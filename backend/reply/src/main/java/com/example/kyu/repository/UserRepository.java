package com.example.kyu.repository;

import com.example.kyu.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInformation, Integer> {

    UserInformation findByUsername(String nickname);
}
