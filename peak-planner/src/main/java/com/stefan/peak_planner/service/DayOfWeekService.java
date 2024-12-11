package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.DayOfWeekDao;
import com.stefan.peak_planner.model.DayOfWeek;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.model.WeekDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DayOfWeekService {

    private DayOfWeekDao dayOfWeekDao;

    public DayOfWeekService(DayOfWeekDao dayOfWeekDao) {
        this.dayOfWeekDao = dayOfWeekDao;
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
}
