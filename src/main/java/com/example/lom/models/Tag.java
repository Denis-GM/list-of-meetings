package com.example.lom.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tag")
public class Tag {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String fullName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private Set<Meeting> meetings = new HashSet<Meeting>();

    public Tag() { }

    public Tag(String tagName, String fullName) {
        this.name = tagName;
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
