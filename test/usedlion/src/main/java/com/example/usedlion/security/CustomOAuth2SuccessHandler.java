package com.example.usedlion.security;

import com.example.usedlion.dto.UserInformation;
import com.example.usedlion.repository.UserInformationRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserInformationRepository userRepo;

    public CustomOAuth2SuccessHandler(UserInformationRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        System.out.println("✅ Entered CustomOAuth2SuccessHandler");

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String email = oauthUser.getAttribute("email");
        UserInformation user = userRepo.findByEmail(email);

        System.out.println("Loaded user: " + user);

        if (user == null) {
            // Create new user from Google login
            user = new UserInformation();
            user.setEmail(email);
            user.setUsername(oauthUser.getAttribute("name")); // optional
            user.setNickname(email.split("@")[0]);
            user.setProvider("google");
            user.setProviderId(oauthUser.getAttribute("sub"));
            user.setRole("USER");
            user.setCreatedAt(java.time.LocalDateTime.now());
            user.setProfileComplete(false);
            userRepo.save(user);
        }

        // Check profile completeness
        if (user.getRegion() == null || user.getRegion().isBlank() || user.getNickname() == null || !user.isProfileComplete()) {
            request.getSession().setAttribute("pendingUser", user);
            response.sendRedirect("/complete-profile");
        } else {
            response.sendRedirect("/dashboard");
        }
    }
}