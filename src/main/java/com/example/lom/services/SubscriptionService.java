package com.example.lom.services;

import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
import com.example.lom.repositories.MeetingRepository;
import com.example.lom.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionService {

    private final MeetingRepository meetingRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    @Autowired
    public SubscriptionService(MeetingRepository meetingRepository, SubscriptionRepository subscriptionRepository, UserService userService) {
        this.meetingRepository = meetingRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    public Optional<Subscription> getSubscriptionById(String id) {
        return this.subscriptionRepository.findById(UUID.fromString(id));
    }

    public Optional<Subscription> getSubscriptionByUserAndMeeting(User user, Meeting meeting) {
        return this.subscriptionRepository.findByUserAndMeeting(user, meeting);
    }

    public void createSubscription(Subscription subscription, Meeting meeting) {
        this.subscriptionRepository.save(subscription);
        meeting.decrementAvailableSeat();
        meetingRepository.save(meeting);
    }

    public void deleteSubscription(String id) {
        this.subscriptionRepository.deleteById(UUID.fromString(id));
    }
}
