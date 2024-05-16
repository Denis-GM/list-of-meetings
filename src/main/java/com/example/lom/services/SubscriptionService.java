package com.example.lom.services;

import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
import com.example.lom.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Optional<Subscription> getSubscriptionById(String id) {
        return this.subscriptionRepository.findById(UUID.fromString(id));
    }

    public Subscription createSubscription(Subscription subscription, User user, Meeting meeting) throws Exception {
        if(!this.subscriptionRepository.existsSubscriptionByUserAndMeeting(user, meeting)){
            this.subscriptionRepository.save(subscription);
            meeting.setAvailableSeats(meeting.getAvailableSeats() - 1);
        }
        else
            throw new Exception("Подпись уже существует");
        return subscription;
    }

    public void deleteSubscription(String id) {
        this.subscriptionRepository.deleteById(UUID.fromString(id));
    }
}
