package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOfWeekDao extends JpaRepository<DayOfWeek, Integer> {
}
