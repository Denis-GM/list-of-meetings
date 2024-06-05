package com.example.lom.controllers;

import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
import com.example.lom.repositories.UserRepository;
import com.example.lom.services.SubscriptionService;
import com.example.lom.services.UserService;
import com.example.lom.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;

@Controller
public class TemplateController {
    private final MeetingService meetingService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public TemplateController(
            MeetingService meetingService,
            SubscriptionService subscriptionService,
            UserService userService,
            UserRepository userRepository) {
        this.meetingService = meetingService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = { "/", }, method = RequestMethod.GET)
    public String index(Model model) {
        var meetings = meetingService.getMeetings();
        model.addAttribute("meetings", meetings);
        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());

        List<Meeting> userMeetings = meetingService.getMeetingsByCreator(currentUser);
        Set<Subscription> userSubscriptions = currentUser.getSubscriptions();

        model.addAttribute("user", currentUser );
        model.addAttribute("userMeetings", userMeetings);
        model.addAttribute("userSubscriptions", userSubscriptions);

        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam String lastName,
                              @RequestParam String firstName,
                              @RequestParam String phone) {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userRepository.findByUsername(authentication.getName());

            currentUser.setLastName(lastName);
            currentUser.setFirstName(firstName);
            currentUser.setPhone(phone);
            userService.updateUser(currentUser);

            return "redirect:/profile";
        }
        catch (Exception ex) {
            return "profile";
        }
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(Model model){
        model.addAttribute("message", "static message");
        return "logout";
    }
}
