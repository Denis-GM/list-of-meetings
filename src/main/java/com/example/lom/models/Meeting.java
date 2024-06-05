package com.example.lom.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="meeting")
public class Meeting {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;

    private Date date;
    private String place;
    private int totalNumberSeats;
    private int availableSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "meeting",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Subscription> subscriptions;

    @ManyToMany
    @JoinTable(
            name = "tags",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public Meeting() { }

    public Meeting(String name, String description, Date date, String place, int totalNumberSeats, int availableSeats, User creator) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.place = place;
        this.totalNumberSeats = totalNumberSeats;
        this.availableSeats = availableSeats;
        this.creator = creator;
    }

    public String getId() {
        return this.id.toString();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getTotalNumberSeats() {
        return totalNumberSeats;
    }

    public void setTotalNumberSeats(int totalNumberSeats) {
        this.totalNumberSeats = totalNumberSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void incrementAvailableSeat() {
        this.availableSeats += 1;
    }

    public void decrementAvailableSeat() {
        this.availableSeats -= 1;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public User getCreator() { return creator; }

    public void setCreator(User creator) { this.creator = creator; }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
