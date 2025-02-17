package com.example.pk_pl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day_of_week")
public class DayOfWeek implements UserOwned{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private WeekDay day;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "dayOfWeek", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    public DayOfWeek() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
