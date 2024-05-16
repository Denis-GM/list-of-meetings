package com.example.lom.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "mg_user")
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id")) @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    // Записи пользователя на мероприятия
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Subscription> subscriptions;

    // Организованные (созданные) пользователем мероприятия
    @OneToMany(mappedBy = "organizer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Meeting> createdMeetings;

    public User() {

    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        roles.add(role);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> singleton) {
        roles.addAll(singleton);
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean b) {
        this.active = b;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Subscription> getRecords() {
        return subscriptions;
    }

    public void setRecords(Set<Subscription> subscriptions) {
        this.subscriptions.addAll(subscriptions);
    }

    public Set<Meeting> getCreatedMeetings() {
        return createdMeetings;
    }

    public void setCreatedMeetings(Set<Meeting> createdMeetings) {
        this.createdMeetings.addAll(createdMeetings);
    }
}
