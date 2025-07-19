package com.example.pk_pl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    @Column(name = "days")
    @Max(value = 32000, message = "You cannot have such a long duration") // 87 years
    private short days;

    @Column(name = "order_index")
    @Min(value = 1, message = "The minimum order is 1")
    private int orderIndex;

    @Column(name = "impact")
    @Min(value = 1, message = "Impact must be at least 1")
    @Max(value = 10, message = "Impact must be at most 10")
    private byte impact;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @JsonIgnore
    private Goal goal;

    @Transient
    private LocalDateTime endDate;

    public Step() {
    }


    public LocalDateTime getEndDate() {
        if (endDate == null && goal != null) {
            LocalDateTime computedStartDate = goal.getStartDate();

            for (Step step : goal.getSteps())
                if (step.getOrderIndex() < this.orderIndex)
                    computedStartDate = computedStartDate.plusDays(step.getDays());

            this.endDate = computedStartDate.plusDays(this.days);
        }
        return this.endDate;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        // Get the previous step's end date, or default to goal start date minus 1 day
        LocalDateTime previousStepEnd = goal.getSteps().stream()
                .filter(s -> s.getOrderIndex() == this.orderIndex - 1)
                .map(Step::getEndDate)
                .findFirst()
                .orElse(goal.getStartDate().minusDays(1))
                .plusDays(1); // fully include the previous step's last day

        LocalDateTime stepEnd = getEndDate().plusDays(1); // include this step's last day

        return now.isAfter(previousStepEnd) && now.isBefore(stepEnd);
    }


    @JsonProperty("progress")
    public int getProgress() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        boolean isGoalStarted = goal.getStartDate().isBefore(now);
        if (!isGoalStarted) return 0;

        if (isActive()) {
            long totalDays = days;

            // Start of this step = end date minus step duration
            LocalDateTime stepStartDate = getEndDate().minusDays(days);
            long elapsedDays = ChronoUnit.DAYS.between(stepStartDate, now);

            return (int) Math.min(100, (elapsedDays * 100) / totalDays);
        }

        return getEndDate().isBefore(now) ? 100 : 0;
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
