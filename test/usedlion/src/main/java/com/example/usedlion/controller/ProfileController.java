package com.example.usedlion.controller;

import com.example.usedlion.dto.UserInformation;
import com.example.usedlion.repository.UserInformationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Controller
public class ProfileController {

    private final UserInformationRepository userRepo;

    public ProfileController(UserInformationRepository userRepo) {
        this.userRepo = userRepo;
    }

    private boolean isProfileComplete(UserInformation user) {
        UserInformation freshUser = userRepo.findByEmail(user.getEmail());
        return freshUser != null && freshUser.isProfileComplete();
    }

    @GetMapping("/complete-profile")
    public String showProfileForm(HttpSession session, Model model) {
        UserInformation pendingUser = (UserInformation) session.getAttribute("pendingUser");
        if (pendingUser == null) {
            return "redirect:/dashboard";
        }

        UserInformation freshUser = userRepo.findByEmail(pendingUser.getEmail());
        if (freshUser != null && freshUser.isProfileComplete()) {
            return "redirect:/dashboard";
        }

        model.addAttribute("user", pendingUser);
        return "complete-profile";
    }

    @PostMapping("/complete-profile")
    public String submitProfile(@ModelAttribute UserInformation updatedUser, HttpSession session) {
        UserInformation pendingUser = (UserInformation) session.getAttribute("pendingUser");
        if (pendingUser == null) {
            return "redirect:/";
        }

        pendingUser.setNickname(updatedUser.getNickname());
        pendingUser.setRegion(updatedUser.getRegion());

        // ✅ Only mark profile complete if required fields are filled
        if (pendingUser.hasRequiredProfileFields()) {
            pendingUser.setProfileComplete(true);
        }

        userRepo.updateProfileFields(pendingUser); // implement this in the repository

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
            pendingUser, auth.getCredentials(), auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        session.removeAttribute("pendingUser");

        return "redirect:/dashboard?signupSuccess=true";
    }
}
