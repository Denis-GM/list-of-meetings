package com.example.lom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TemplateController {
    private final MeetingApiController meetingApiController;

    @Autowired
    public TemplateController(MeetingApiController MeetingApiController) {
        this.meetingApiController = MeetingApiController;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        var meetings = meetingApiController.getMeetings();
        model.addAttribute("message", "static message");
        model.addAttribute("meetings", meetings);
        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("message", "static message");
        return "login";
    }
}
