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

    //  Изменить формат у tags и recordedUsers
    private int tags = 0;
    private  int recordedUsers = 0;

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
}
