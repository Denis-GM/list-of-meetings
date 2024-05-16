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
}
