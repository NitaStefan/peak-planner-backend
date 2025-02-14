package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.ActivityDao;
import com.stefan.peak_planner.dao.DayOfWeekDao;
import com.stefan.peak_planner.dao.GoalDao;
import com.stefan.peak_planner.dto.ScheduleUpdateRequest;
import com.stefan.peak_planner.exception.ResourceNotFoundException;
import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.model.DayOfWeek;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.model.WeekDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<DayOfWeek> updateSchedule(User currentUser, ScheduleUpdateRequest request) {

        // 1. Delete Activities by IDs (batch delete)
        if (request.getIdsToDelete() != null && !request.getIdsToDelete().isEmpty()) {
            activityDao.deleteAllById(request.getIdsToDelete());
        }

        // 2. Process New Activities per Day
        if (request.getActivitiesToAdd() != null && !request.getActivitiesToAdd().isEmpty()) {

            List<Activity> allActivitiesToSave = new ArrayList<>();

            for (Map.Entry<WeekDay, List<Activity>> entry : request.getActivitiesToAdd().entrySet()) {
                WeekDay weekDayEnum = entry.getKey();
                List<Activity> activities = entry.getValue();

                // Skip if the activities list is empty (avoid unnecessary DB calls)
                if (activities.isEmpty()) continue;

                // Fetch the correct DayOfWeek for the current user
                DayOfWeek dayOfWeek = getDayOfWeek(currentUser, weekDayEnum);
                for (Activity activity : activities) {
                    activity.setDayOfWeek(dayOfWeek);

                    // If goal ID exists, fetch and set goal reference
                    if (activity.getRequestGoalId() > 0) {
                        activity.setGoal(goalDao.findById(activity.getRequestGoalId())
                                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + activity.getRequestGoalId())));
                    }

                    allActivitiesToSave.add(activity);
                }
            }

            // Save all activities in one batch
            if (!allActivitiesToSave.isEmpty()) {
                activityDao.saveAll(allActivitiesToSave);
            }
        }

        return dayOfWeekDao.findAll(); // Return the updated week schedule
    }


//    @Transactional
//    public List<DayOfWeek> saveAll(List<DayOfWeek> daysOfWeek) {
//
//        for (DayOfWeek dayOfWeek : daysOfWeek)
//            dayOfWeek.getActivities().forEach(activity -> {
//                activity.setDayOfWeek(dayOfWeek);
//                if (activity.getRequestGoalId() > 0) activity.setGoal(goalDao.findById(activity.getRequestGoalId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + activity.getRequestGoalId())));
//
//            });
//
//        return dayOfWeekDao.saveAll(daysOfWeek);
//    }
}
