package com.example.pk_pl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "event_details")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EventDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(max = 45, message = "The title should be at most 45 characters")
    private String title;

    @Column(name = "description")
    @Size(max = 400, message = "Description is too long (max. 400 characters)")
    private String description;

    @Column(name = "start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @Column(name = "minutes")
    @Max(value = 1440, message = "You cannot have that many minutes in a day")
    private short minutes;

    @ManyToOne
    @JoinColumn(name = "planned_event_id")
    @JsonIgnore
    private PlannedEvent plannedEvent;

    public EventDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public short getMinutes() {
        return minutes;
    }

    public void setMinutes(short minutes) {
        this.minutes = minutes;
    }

    public PlannedEvent getPlannedEvent() {
        return plannedEvent;
    }

    public void setPlannedEvent(PlannedEvent plannedEvent) {
        this.plannedEvent = plannedEvent;
    }
}
