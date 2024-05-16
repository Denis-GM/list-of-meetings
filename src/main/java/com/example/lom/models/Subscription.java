package com.example.lom.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="Subscription")
public class Subscription {
    @Id
    @GeneratedValue
    private UUID id;

    private Date dateSubscription = new Date();

    // Пользователь, подписавшийся на мероприятие
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_subscriber_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="meeting_id")
    private Meeting meeting;

    public Subscription() {

    }

    public Subscription(User user, Meeting meeting) {
        this.user = user;
        this.meeting = meeting;
        this.dateSubscription = new Date();
    }

    public String getId() {
        return this.id.toString();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public Date getDateSubscription() {
        return dateSubscription;
    }

    public void setDateSubscription(Date dateSubscription) {
        this.dateSubscription = dateSubscription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meetings) {
        this.meeting = meetings;
    }
}
