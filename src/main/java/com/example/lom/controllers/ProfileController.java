package com.example.lom.controllers;

import com.example.lom.models.User;
import com.example.lom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;
//
//    @GetMapping("/profile")
//    public String userProfile(Model model) {
//        String currentUser = userService.getCurrentUser();
//        model.addAttribute("user", currentUser);
//        return "profile";
//    }
}
