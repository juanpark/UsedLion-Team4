package com.example.usedlion.controller;

import com.example.usedlion.dto.UserInformation;
import com.example.usedlion.repository.UserInformationRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class SignupController {
    private final UserInformationRepository userRepo;
    private final PasswordEncoder passwordEncoder; //비밀번호 BCrypt로 저장
    public SignupController(UserInformationRepository userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute UserInformation user) {
        user.setProvider("local");
        user.setProviderId(null);
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());

        user.setPassword(passwordEncoder.encode(user.getPassword())); // BCrypt 해싱
        userRepo.save(user);
        return "redirect:/";  // 로그인 페이지로 리디렉션
    }
}