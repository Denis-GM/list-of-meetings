package com.example.lom.controllers;

import com.example.lom.models.Meeting;
import com.example.lom.models.Record;
import com.example.lom.models.User;
import com.example.lom.repositories.UserRepository;
import com.example.lom.services.MeetingService;
import com.example.lom.services.RecordService;
import com.example.lom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class RecordApiController {
    private final RecordService recordService;
    private final MeetingService meetingService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public RecordApiController(RecordService recordService, MeetingService meetingService,
                               UserService userService, UserRepository userRepository) {
        this.recordService = recordService;
        this.meetingService = meetingService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


//  Исправить метод
    @RequestMapping(value = "/{meetingId}", method={RequestMethod.POST, RequestMethod.GET})
    Record postRecord(@PathVariable String meetingId) {
        var record = new Record();
        var meeting = this.meetingService.getMeetingById(meetingId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userRepository.findByUsername(authentication.getName());
//        user.setRecord(record);
        return this.recordService.postRecord(record);
    }

}
