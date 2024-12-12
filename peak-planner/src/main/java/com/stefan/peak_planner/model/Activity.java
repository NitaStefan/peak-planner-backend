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

    @Column(name = "name")
    @Size(max = 45, message = "The name of the activity should be at most 45 characters")
    private String name;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "priority")
    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 10, message = "Priority must be at most 10")
    private byte priority;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityStep> activitySteps = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public List<ActivityStep> getActivitySteps() {
        return activitySteps;
    }

    public void setActivitySteps(List<ActivityStep> activitySteps) {
        this.activitySteps = activitySteps;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
