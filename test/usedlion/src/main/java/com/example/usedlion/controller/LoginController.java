package com.example.usedlion.controller;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
        String nickname = (String) principal.getAttributes().get("name");
        model.addAttribute("nickname", nickname);  // Thymeleaf 템플릿으로 전달
        return "redirectChat";
    }
}
