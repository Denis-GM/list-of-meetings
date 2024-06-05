package com.example.lom.controllers;

import com.example.lom.models.metingPayload.MetingPayload;
import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
import com.example.lom.services.MeetingService;
import com.example.lom.services.SubscriptionService;
import com.example.lom.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    private final UserService userService;

    private final SubscriptionService subscriptionService;

    @Autowired
    public MeetingController(MeetingService meetingService, UserService userService, SubscriptionService subscriptionService) {
        this.meetingService = meetingService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/{id}")
    public String meetingPage(Model model, @PathVariable String id, Authentication authentication) {
        User currentUser  = userService.getUserByUsername(authentication.getName());

        boolean isCreator = false;
        Meeting meeting = meetingService.getMeetingById(id).stream().findFirst().orElse(null);
        if(meeting != null)
            isCreator= meeting.getCreator().equals(currentUser);
        Subscription subscription = this.subscriptionService.getSubscriptionByUserAndMeeting(currentUser, meeting).stream().findFirst().orElse(null);

        model.addAttribute("meeting", meeting);
        model.addAttribute("isCreator", isCreator);
        model.addAttribute("subscription", subscription);
        return "meeting";
    }

    @GetMapping("/create")
    public String meetingCreatePage(Model model) {
        model.addAttribute("meeting", new Meeting());
        return "createMeeting";
    }

    @PostMapping("/create")
    public String createMeeting(@Valid MetingPayload payload, BindingResult bindingResult,
                                    Authentication authentication, Model model) {
        User currentUser  = userService.getUserByUsername(authentication.getName());
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("payload", payload);
//            model.addAttribute("errors", bindingResult.getAllErrors().stream()
//                    .map(ObjectError::getDefaultMessage)
//                    .toList());
//            return "createMeeting";
//        }
//        else {
            Meeting meeting = new Meeting(
                    payload.title(),
                    payload.details(),
//                    payload.date(),
                    new Date(),
                    payload.place()
                    ,payload.totalNumberSeats(),
                    currentUser);
            this.meetingService.addMeeting(meeting);
            return "redirect:/";
//        }
    }

    @RequestMapping(value = "/delete/{id}", method={RequestMethod.DELETE, RequestMethod.POST})
    public String deleteMeeting(@PathVariable String id, Authentication authentication) {
        User currentUser = userService.getUserByUsername(authentication.getName());
        Meeting meeting = meetingService.getMeetingById(id).stream().findFirst().orElse(null);
        if(meeting != null) {
            if(meeting.getCreator().equals(currentUser)){
                this.meetingService.deleteMeeting(id);
                return "redirect:/profile";
            }
        }
        return "redirect:/meetings/{id}";
    }

    @RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
    public String meetingEditPage(Model model, @PathVariable String id) {
        Meeting meeting = meetingService.getMeetingById(id).stream().findFirst().orElse(null);;
        model.addAttribute("meeting", meeting);
        return "createMeeting";
    }
}
