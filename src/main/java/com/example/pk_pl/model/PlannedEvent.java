package com.example.pk_pl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planned_event")
public class PlannedEvent implements UserOwned{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @OneToMany(mappedBy = "plannedEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventDetails> eventDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public PlannedEvent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public List<EventDetails> getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(List<EventDetails> eventDetails) {
        this.eventDetails = eventDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
