package com.example.lom.controllers;

import com.example.lom.models.Meeting;
import com.example.lom.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class TemplateController {
    private final MeetingService meetingService;

    @Autowired
    public TemplateController(MeetingService meetingService) {
        this.meetingService = meetingService;
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
}
