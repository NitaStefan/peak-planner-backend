package com.stefan.peak_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(max = 45, message = "The title of the activity should be at most 45 characters")
    private String title;

    @Column(name = "description")
    @Size(max = 400, message = "Description is too long (max. 400 characters)")
    private String description;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "minutes")
    @Max(value = 1440, message = "You cannot have that many minutes in a day")
    private short minutes;

    @Column(name = "impact")
    @Min(value = 1, message = "Impact must be at least 1")
    @Max(value = 10, message = "Impact must be at most 10")
    private byte impact;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = true)
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "day_of_week_id")
    @JsonIgnore
    private DayOfWeek dayOfWeek;

    public Activity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    @Transient
    public String getTitle() {
//        if (goal != null) {
//            Step currentStep = goal.getCurrentStep();
//            return (currentStep != null) ? currentStep.getTitle() : "No Active Step";
//        }
        return title; // Fallback if no goal is set
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

//    @Transient
    public String getDescription() {
//        if (goal != null) {
//            Step currentStep = goal.getCurrentStep();
//            return (currentStep != null) ? currentStep.getDescription() : "No Active Step Description";
//        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getMinutes() {
        return minutes;
    }

    public void setMinutes(short minutes) {
        this.minutes = minutes;
    }

    public byte getImpact() {
        return impact;
    }

    public void setImpact(byte impact) {
        this.impact = impact;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

}
