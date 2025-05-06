package com.example.usedlion.controller;

import com.example.usedlion.dto.Post;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;

@Controller // @RestController와는 별도!
public class PageController {

    @GetMapping("/")
    public String landing(Model model){
        model.addAttribute("hideHeader", "true");
        model.addAttribute("hideFooter", true);
        return "landing";   // 로그인 화면
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OidcUser principal, Model model) {
        if (principal != null) {
            model.addAttribute("userName", principal.getFullName());
        }

        // Example post data — replace with real DB call
        List<Post> posts = List.of(
                new Post("아이폰 13", "전자기기", "최상", "2025-05-06", "/images/iphone13.jpg"),
                new Post("의자", "가구", "좋음", "2025-05-05", "/images/chair.jpg"),
                new Post("의자", "가구", "좋음", "2025-05-05", "/images/chair.jpg"),
                new Post("의자", "가구", "좋음", "2025-05-05", "/images/chair.jpg")

        );
        model.addAttribute("posts", posts);

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat"; //.html 필요없음...
    }
}
