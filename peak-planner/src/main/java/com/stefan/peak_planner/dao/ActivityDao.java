package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDao extends JpaRepository<Activity, Integer> {
}
