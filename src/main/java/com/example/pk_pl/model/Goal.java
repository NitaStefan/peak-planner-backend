package com.example.pk_pl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goal")
public class Goal implements UserOwned{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(max = 45, message = "The title should be at most 45 characters")
    private String title;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    @OrderBy("orderIndex ASC")
    @JsonIgnore
    private List<Step> steps = new ArrayList<>();

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Activity> activities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Goal() {
    }

    @JsonProperty("numberOfSteps")
    public int getNumberOfSteps() {
        return steps.size();
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Step getCurrentStep() {
        return steps.stream()
                .filter(Step::isActive)
                .findFirst()
                .orElse(null);
    }
}
