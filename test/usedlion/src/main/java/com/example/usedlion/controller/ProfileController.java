package com.example.usedlion.controller;

import com.example.usedlion.dto.UserInformation;
import com.example.usedlion.repository.UserInformationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final UserInformationRepository userRepo;

    public ProfileController(UserInformationRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/complete-profile")
    public String showProfileForm(HttpSession session, Model model) {
        UserInformation pendingUser = (UserInformation) session.getAttribute("pendingUser");
        if (pendingUser == null) {
            System.out.println("⚠️ No pending user in session!");
            return "redirect:/dashboard";
        }
        System.out.println("✅ Showing profile form for: " + pendingUser.getEmail());
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
        pendingUser.setProfileComplete(true);

        userRepo.updateProfileFields(pendingUser); // implement this in the repository
        session.removeAttribute("pendingUser");

        return "redirect:/dashboard";
    }
}
