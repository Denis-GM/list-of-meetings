package com.example.lom.repositories;

import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, UUID> {
    boolean existsSubscriptionByUser(User user);
    boolean existsSubscriptionByMeeting(Meeting meeting);
    boolean existsSubscriptionByUserAndMeeting(User user, Meeting meeting);
}