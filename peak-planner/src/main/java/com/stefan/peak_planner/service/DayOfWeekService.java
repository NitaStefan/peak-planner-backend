package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.ActivityDao;
import com.stefan.peak_planner.dao.DayOfWeekDao;
import com.stefan.peak_planner.exception.ResourceNotFoundException;
import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.model.DayOfWeek;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.model.WeekDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DayOfWeekService {

    private final DayOfWeekDao dayOfWeekDao;

    private final ActivityDao activityDao;

    public DayOfWeekService(DayOfWeekDao dayOfWeekDao, ActivityDao activityDao) {
        this.dayOfWeekDao = dayOfWeekDao;
        this.activityDao = activityDao;
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

    @Transactional
    public Activity addActivityToDayOfWeek(int dayId, Activity activity) {

        DayOfWeek day = dayOfWeekDao.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("Day not found"));

        activity.setDayOfWeek(day);

        return activityDao.save(activity);
    }

    public DayOfWeek findDayOfWeekById(int dayId) {

        return dayOfWeekDao.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("Day not found"));
    }
}
