package com.example.lom.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="record")
public class Record {
    @Id
    @GeneratedValue
    private UUID id;

    private Date dateRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mg_user_id")
    private User user;

    public Record() {
//        this.dateRecord = new Date();
    }

    public Record(User user) {
        this.dateRecord = new Date();
        this.user = user;
    }

    public String getId() {
        return this.id.toString();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public Date getDateRecord() {
        return dateRecord;
    }

    public void setDateRecord(Date dateRecord) {
        this.dateRecord = dateRecord;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
