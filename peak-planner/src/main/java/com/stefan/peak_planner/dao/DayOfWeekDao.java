package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.DayOfWeek;
import com.stefan.peak_planner.model.Goal;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.model.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayOfWeekDao extends JpaRepository<DayOfWeek, Integer> {
    Optional<DayOfWeek> findByUserAndDay(User user, WeekDay day);

    List<DayOfWeek> findByUser(User user);
}
