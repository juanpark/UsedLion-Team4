package com.example.usedlion.controller;

import com.example.usedlion.security.CustomUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller // @RestController와는 별도!
public class PageController {

    @GetMapping("/")
    public String landing(){
        return "landing";   // 로그인 화면
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal OidcUser oidcUser,
            @AuthenticationPrincipal OAuth2User oauth2User,
            Authentication authentication,
            Model model
    ) {
        String name = "Guest";
        if (oidcUser != null) {
            name = oidcUser.getFullName();
        }
        else if (oauth2User != null && oauth2User.getAttribute("name") != null) {
            name = oauth2User.getAttribute("name");                             // OAuth2User에서 이름 꺼내기
        }
        else if (authentication != null
                && authentication.getPrincipal() instanceof UserDetails) {
            name = ((UserDetails) authentication.getPrincipal()).getUsername();  // 폼 로그인 사용자명 가져오기
        }
        model.addAttribute("userName", name);
        return "dashboard";
    }


    @GetMapping("/chat")
    public String chat(
            @AuthenticationPrincipal OAuth2User oauth2User,
            Authentication authentication,
            Model model
    ) {
        String nickname = "Guest";

        // ① OAuth2 로그인 (구글) 사용자의 프로필 이름이 곧 nickname
        if (oauth2User != null && oauth2User.getAttribute("name") != null) {
            nickname = oauth2User.getAttribute("name");
        }
        // ② 로컬 로그인 사용자는 UserInformationRepository를 이용해 DB에서 nickname 조회
        else if (authentication != null
                && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
            nickname = cud.getUser().getNickname();  // getUser() → UserInformation DTO
        }

        model.addAttribute("nickname", nickname);
        return "chat";
    }
}
