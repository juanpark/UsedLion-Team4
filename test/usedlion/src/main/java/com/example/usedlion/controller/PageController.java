package com.example.usedlion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Controller // @RestController와는 별도!
public class PageController {

    @GetMapping("/")
    public String landing(){
        return "landing";   // 로그인 화면
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OidcUser principal, Model model) {
        if (principal != null) {
            model.addAttribute("userName", principal.getFullName());
        } else {
            model.addAttribute("userName", "Guest");
        }
        return "dashboard";
    }


    @GetMapping("/chat")
    public String chat() {
        return "chat"; //.html 필요없음...
    }
}
