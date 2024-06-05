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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionService {

    private final MeetingRepository meetingRepository;

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(MeetingRepository meetingRepository, SubscriptionRepository subscriptionRepository) {
        this.meetingRepository = meetingRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Optional<Subscription> getSubscriptionById(String id) {
        return subscriptionRepository.findById(UUID.fromString(id));
    }

    public Optional<Subscription> getSubscriptionByUserAndMeeting(User user, Meeting meeting) {
        return subscriptionRepository.findByUserAndMeeting(user, meeting);
    }

    public void createSubscription(Subscription subscription, Meeting meeting) {
        subscriptionRepository.save(subscription);
        meeting.decrementAvailableSeat();
        meetingRepository.save(meeting);
    }

//    public void deleteSubscription(String id) {
//        var sub = subscriptionRepository.findById(UUID.fromString(id));
//        subscriptionRepository.deleteById(UUID.fromString(id));
//    }

    public void deleteSubscription(Subscription subscription, Meeting meeting) {
        subscriptionRepository.delete(subscription);
        meeting.incrementAvailableSeat();
        meetingRepository.save(meeting);
    }
}
