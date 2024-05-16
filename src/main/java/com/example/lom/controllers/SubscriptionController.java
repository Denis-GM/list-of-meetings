package com.example.lom.controllers;

import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
import com.example.lom.repositories.SubscriptionRepository;
import com.example.lom.services.MeetingService;
import com.example.lom.services.SubscriptionService;
import com.example.lom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final SubscriptionRepository subscriptionRepository;
    private final MeetingService meetingService;
    private final UserService userService;

    @Autowired
    public SubscriptionController(
            SubscriptionService subscriptionService,
            SubscriptionRepository subscriptionRepository,
            MeetingService meetingService,
            UserService userService) {
        this.subscriptionService = subscriptionService;
        this.subscriptionRepository = subscriptionRepository;
        this.meetingService = meetingService;
        this.userService = userService;
    }

//  Добавить проверку на повторение
    @RequestMapping(value = "/subscribe/{meetingId}", method={RequestMethod.POST, RequestMethod.GET})
    public Subscription postSubscription(@PathVariable String meetingId, Authentication authentication) throws Exception {
        Meeting meeting = this.meetingService.getMeetingById(meetingId)
                .stream().findFirst().orElse(null);
        User user = this.userService.findUserByUsername(authentication.getName());
        var subscription = new Subscription(user, meeting);
        return this.subscriptionService.createSubscription(subscription, user, meeting);
    }

    @RequestMapping(value = "/unsubscribe/{meetingId}", method={RequestMethod.DELETE, RequestMethod.GET})
//    @DeleteMapping("/unsubscribe/{meetingId}")
    void deleteSubscription(@PathVariable String meetingId, Authentication authentication) {
        Meeting meeting = this.meetingService.getMeetingById(meetingId)
                .stream().findFirst().orElse(null);
        if(meeting != null){
            User user = this.userService.findUserByUsername(authentication.getName());
            meeting.setAvailableSeats(meeting.getAvailableSeats() + 1);

//            Subscription subscription = this.subscriptionRepository.existsSubscriptionByUserAndMeeting(user, meeting);
//            this.subscriptionService.deleteSubscription();
        }
    }
}
