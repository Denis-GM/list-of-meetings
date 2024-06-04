package com.example.lom.services;

import com.example.lom.models.Meeting;
import com.example.lom.models.User;
import com.example.lom.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final UserService userService;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, UserService userService) {
        this.meetingRepository = meetingRepository;
        this.userService = userService;

/*        User user = this.userService.findUserById("c6428aa4-4c16-4b7a-8cbe-a250e95227e0");
        var userByUsername = this.userService.loadUserByUsername("denis");
       this.meetingRepository.saveAll(List.of(
               new Meeting("Настольная игра 1", "Описание 1", new Date(),
                      "Екатеринбург", 6, user),
              new Meeting("Настольная игра 2", "Описание 2", new Date(),
                       "Екатеринбург", 4, user),
              new Meeting("Настольная игра 3", "Описание 3", new Date(), "Екатеринбург",
                     12, user)
      ));*/
    }

    public List<Meeting> getMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        this.meetingRepository.findAll().forEach(meetings::add);
        return meetings;
    }

    public Optional<Meeting> getMeetingById(String id) {
        return this.meetingRepository.findById(UUID.fromString(id));
    }

    public Meeting postMeeting(Meeting meeting) {
        this.meetingRepository.save(meeting);
        return meeting;
    }

    public ResponseEntity<Meeting> putMeeting(String id, Meeting meeting) {
        return (!this.meetingRepository.existsById(UUID.fromString(id)))
                ? new ResponseEntity<>(this.meetingRepository.save(meeting),
                HttpStatus.CREATED)
                : new ResponseEntity<>(this.meetingRepository.save(meeting),
                HttpStatus.OK);
    }

    public void deleteMeeting(String id) {
        this.meetingRepository.deleteById(UUID.fromString(id));
    }

    public List<Meeting> getMeetingsByCreator(User creator) {
        return meetingRepository.findByCreator(creator);
    }
}
