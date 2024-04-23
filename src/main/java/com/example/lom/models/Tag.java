package com.example.lom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="tag")
public class Tag {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag() {

    }

    public Tag(String tagName) {
        this.name = tagName;
    }
}
