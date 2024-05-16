package com.example.lom.controllers;

import com.example.lom.models.Meeting;
import com.example.lom.models.Record;
import com.example.lom.models.User;
import com.example.lom.repositories.UserRepository;
import com.example.lom.services.UserService;
import com.example.lom.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
public class TemplateController {
    private final MeetingService meetingService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public TemplateController(MeetingService meetingService, UserService userService, UserRepository userRepository) {
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

    @RequestMapping(value = { "/meetings/{id}" }, method = RequestMethod.GET)
    public String meetingPage(Model model, @PathVariable String id) {
        Meeting meeting = meetingService.getMeetingById(id).stream().findFirst().orElse(null);;
        model.addAttribute("meeting", meeting);
        return "meeting";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("message", "static message");
        return "login";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser  = userRepository.findByUsername(authentication.getName());

        List<Meeting> userMeetings = meetingService.getMeetingsByCreator(currentUser );
        Set<Record> userRecords = currentUser .getRecords();

        model.addAttribute("user", currentUser );
        model.addAttribute("userMeetings", userMeetings);
        model.addAttribute("userRecords", userRecords);

        return "profile";
    }

//    @GetMapping("/profile")
//    public String userProfile(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        String username = userDetails.getUsername();
//
//        User currentUser = userRepository.findByUsername(username);
//
//        model.addAttribute("user", currentUser);
//
//        return "profile";
//    }

    @PostMapping("/edit")
    public String editProfile(@RequestParam("lastName") String lastName,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("phone") String phone) {
        try
        {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            User currentUser = userRepository.findByUsername(authentication.getName());
//
//            currentUser.setLastName(lastName);
//            currentUser.setFirstName(firstName);
//            currentUser.setPhone(phone);

            System.out.println("New Last Name: " + lastName);
            System.out.println("New First Name: " + firstName);
            System.out.println("New Phone: " + phone);

            //userService.updateUser(currentUser);

            return "redirect:/profile";
        }
        catch (Exception ex) {
            return "profile";
        }
    }
}
