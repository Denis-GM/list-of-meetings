package com.example.lom.controllers;
import com.example.lom.models.User;
import com.example.lom.repositories.UserRepository;
import com.example.lom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        User currentUser = userRepository.findByUsername(username);

        model.addAttribute("user", currentUser);

        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam("userId") UUID userId,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("phone") String phone,
                              Model model) {
        userService.updateUser(userId, lastName, firstName, phone);
        return "redirect:/profile";
    }
}
