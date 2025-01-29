package com.stefan.peak_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "step")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(max = 45, message = "The title should be at most 45 characters")
    private String title;

    @Column(name = "description")
    @Size(max = 400, message = "Description is too long (max. 400 characters). Try to be more concise or create a new step")
    private String description;

    @Column(name = "days")
    @Max(value = 32000, message = "You cannot have such a long duration") // 87 years
    private short days;

    @Column(name = "order_index")
    @Min(value = 1, message = "The minimum order is 1")
    private int orderIndex;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @JsonIgnore
    private Goal goal;

    @Transient
    private LocalDate endDate;

    @Transient
    private boolean isActive;

    public Step() {
    }

    public LocalDate getEndDate() {
        if (endDate == null && goal != null) { // Compute only if not already set
            LocalDate computedStartDate = goal.getStartDate();

            for (Step step : goal.getSteps()) {
                if (step.getOrderIndex() < this.orderIndex) {
                    computedStartDate = computedStartDate.plusDays(step.getDays());
                }
            }

            this.endDate = computedStartDate.plusDays(this.days);
        }
        return this.endDate;
    }

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        LocalDate stepEndDate = getEndDate(); // Ensure end date is calculated

        if (goal == null || stepEndDate == null) {
            return false;
        }

        // Get the previous step's end date, or default to goal start date if first step
        LocalDate previousStepEndDate = goal.getSteps().stream()
                .filter(s -> s.getOrderIndex() == this.orderIndex - 1)
                .map(Step::getEndDate)
                .findFirst()
                .orElse(goal.getStartDate());

        // Current step is active only if today is strictly after the previous step's end date and before its own end date
        return today.isAfter(previousStepEndDate) && !today.isAfter(stepEndDate);
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


    public short getDays() {
        return days;
    }

    public void setDays(short days) {
        this.days = days;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
