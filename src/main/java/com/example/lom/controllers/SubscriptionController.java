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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionService subscriptionService;

    private final MeetingService meetingService;

    private final UserService userService;

    @Autowired
    public SubscriptionController(
            SubscriptionService subscriptionService,
            SubscriptionRepository subscriptionRepository,
            MeetingService meetingService,
            UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
        this.meetingService = meetingService;
        this.userService = userService;
    }

    @RequestMapping(value = "/subscribe/{meetingId}", method={RequestMethod.POST, RequestMethod.GET})
    public String createSubscription(@PathVariable String meetingId, Authentication authentication) {
        User user = this.userService.getUserByUsername(authentication.getName());
        Meeting meeting = this.meetingService.getMeetingById(meetingId).stream().findFirst()
                .orElse(null);

        if(meeting != null && !meeting.getCreator().equals(user) && meeting.getAvailableSeats() > 0) {
            if(!this.subscriptionRepository.existsSubscriptionByUserAndMeeting(user, meeting)) {
                var subscription = new Subscription(user, meeting);
                this.subscriptionService.createSubscription(subscription, meeting);
            }
        }
        return "redirect:/meetings/{meetingId}";
    }

    @RequestMapping(value = "/unsubscribe/{meetingId}/{subscriptionId}",
            method={RequestMethod.DELETE, RequestMethod.GET})
    public String deleteSubscription(@PathVariable("meetingId") String meetingId,
                                   @PathVariable("subscriptionId") String subscriptionId,
                                   Authentication authentication) {
        User user = this.userService.getUserByUsername(authentication.getName());
        Meeting meeting = this.meetingService.getMeetingById(meetingId)
                .stream().findFirst().orElse(null);

        if(meeting != null && !meeting.getCreator().equals(user)) {
            var isSubscribed = false;
            for (var sub : meeting.getSubscriptions()) {
                if (sub.getUser().equals(user)) {
                    isSubscribed = true;
                    break;
                }
            }
            if(isSubscribed) {
                subscriptionService.deleteSubscription(subscriptionId);
                meeting.incrementAvailableSeat();
                meetingService.updateMeeting(meeting);
            }
        }
        return "redirect:/meetings/{meetingId}";
    }
}
