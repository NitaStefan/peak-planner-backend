package com.example.pk_pl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.*;

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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "minutes")
    @Max(value = 1440, message = "You cannot have that many minutes in a day")
    private short minutes;

    @Column(name = "impact")
    @Min(value = 1, message = "Impact must be at least 1")
    @Max(value = 10, message = "Impact must be at most 10")
    private byte impact;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = true)
    @JsonIgnore
    private Goal goal;

    @Transient
    @JsonProperty(value = "goalId", access = JsonProperty.Access.WRITE_ONLY)
    private int goalId;

    @ManyToOne
    @JoinColumn(name = "day_of_week_id")
    @JsonIgnore
    private DayOfWeek dayOfWeek;

    public Activity() {
        this.impact = 1;
    }

    public LocalDateTime getEndTime() {

        return startTime.plusMinutes(minutes);
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        if (startTime == null) {
            return false;
        }

        // Extract the LocalTime parts (hh:mm) only
        LocalTime nowTime = LocalTime.now(ZoneOffset.UTC).plusHours(1);
        LocalTime start = startTime.toLocalTime();
        LocalTime end = getEndTime().toLocalTime();

        // Check if the current time is between start and end
        return !nowTime.isBefore(start) && nowTime.isBefore(end);
    }


    @Transient
    @JsonProperty("goalTitle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGoalTitle() {
        return (goal != null) ? goal.getTitle() : null;
    }

    @JsonIgnore
    public int getRequestGoalId() {
        return goalId;
    }

    @Transient
    @JsonProperty("goalId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getRealGoalId() {
        return (goal != null) ? goal.getId() : null;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        if (goal != null) {
            Step currentStep = goal.getCurrentStep();
            return (currentStep != null) ? currentStep.getTitle() : "No Active Step";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
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
        if (goal != null) {
            Step currentStep = goal.getCurrentStep();
            return (currentStep != null) ? currentStep.getDescription() : "";
        }
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public byte getImpact() {
        if (goal != null) {
            Step currentStep = goal.getCurrentStep();
            return (currentStep != null) ? currentStep.getImpact() : 0;
        }
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
