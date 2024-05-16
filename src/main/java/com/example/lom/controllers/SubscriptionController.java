package com.example.lom.controllers;

import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
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
    private final MeetingService meetingService;
    private final UserService userService;

    @Autowired
    public SubscriptionController(
            SubscriptionService subscriptionService,
            MeetingService meetingService,
            UserService userService) {
        this.subscriptionService = subscriptionService;
        this.meetingService = meetingService;
        this.userService = userService;
    }

//  Добавить проверку на повторение
    @RequestMapping(value = "/{meetingId}", method={RequestMethod.POST, RequestMethod.GET})
    Subscription postRecord(@PathVariable String meetingId, Authentication authentication) throws Exception {
        Meeting meeting = this.meetingService.getMeetingById(meetingId)
                .stream().findFirst().orElse(null);
        User user = this.userService.findUserByUsername(authentication.getName());
        var subscription = new Subscription(user, meeting);
        return this.subscriptionService.createSubscription(subscription, user, meeting);
    }

}
