package com.stefan.peak_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskStep> taskSteps = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @JsonIgnore
    private Goal goal;

    public Task() {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<TaskStep> getTaskSteps() {
        return taskSteps;
    }

    public void setTaskSteps(List<TaskStep> taskSteps) {
        this.taskSteps = taskSteps;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
