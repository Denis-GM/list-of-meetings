package com.example.lom.controllers;

import com.example.lom.models.Meeting;
import com.example.lom.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    Iterable<Meeting> getMeetings() {
        return meetingService.getMeetings();
    }

    @GetMapping(value = "/{id}")
    Optional<Meeting> getMeetingById(@PathVariable String id) {
        return this.meetingService.getMeetingById(id);
    }

    @PostMapping
    Meeting postMeeting(@RequestBody Meeting meeting) {
        return this.meetingService.postMeeting(meeting);
    }

    @PutMapping("/{id}")
    ResponseEntity<Meeting> putMeeting(@PathVariable String id, @RequestBody Meeting meeting) {
        return this.meetingService.putMeeting(id, meeting);
    }

    @DeleteMapping("/{id}")
    void deleteMeeting(@PathVariable String id) {
        this.meetingService.deleteMeeting(id);
    }
}
