package com.example.lom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
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
    private int totalNumberSeats = 0;
    private int availableSeats = 0;

    private int tags = 0;
    private  int recordedUsers = 0;

    public Meeting() {

    }

    public Meeting(String name, String description, Date date, String place, int totalNumberSeats) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.place = place;
        this.totalNumberSeats = totalNumberSeats;
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

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
