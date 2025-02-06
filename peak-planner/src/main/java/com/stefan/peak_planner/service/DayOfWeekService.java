package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.ActivityDao;
import com.stefan.peak_planner.dao.DayOfWeekDao;
import com.stefan.peak_planner.dao.GoalDao;
import com.stefan.peak_planner.exception.ResourceNotFoundException;
import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.model.DayOfWeek;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.model.WeekDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DayOfWeekService {

    private final DayOfWeekDao dayOfWeekDao;

    private final ActivityDao activityDao;

    private final GoalDao goalDao;

    public DayOfWeekService(DayOfWeekDao dayOfWeekDao, ActivityDao activityDao, GoalDao goalDao) {
        this.dayOfWeekDao = dayOfWeekDao;
        this.activityDao = activityDao;
        this.goalDao = goalDao;
    }

    @Transactional
    public void createDaysOfWeek(User user) {

        List<DayOfWeek> daysOfWeek = new ArrayList<>();

        WeekDay[] weekDays = WeekDay.values();

        for (WeekDay weekDay : weekDays) {
            DayOfWeek day = new DayOfWeek();
            day.setUser(user);
            day.setDay(weekDay);
            daysOfWeek.add(day);
        }

        dayOfWeekDao.saveAll(daysOfWeek);
    }

    public DayOfWeek getDayOfWeek(User currentUser, WeekDay day) {
        return dayOfWeekDao.findByUserAndDay(currentUser, day)
                .orElseThrow(() -> new NoSuchElementException("Day not found for user"));
    }

    public List<DayOfWeek> getDaysOfWeek(User currentUser) {

        return dayOfWeekDao.findByUser(currentUser);
    }

    @Transactional
    public List<DayOfWeek> saveAll(List<DayOfWeek> daysOfWeek) {

        for (DayOfWeek dayOfWeek : daysOfWeek)
            dayOfWeek.getActivities().forEach(activity -> {
                activity.setDayOfWeek(dayOfWeek);
                if (activity.getGoalId() > 0) activity.setGoal(goalDao.findById(activity.getGoalId())
                        .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + activity.getGoalId())));

            });

        return dayOfWeekDao.saveAll(daysOfWeek);
    }
}
