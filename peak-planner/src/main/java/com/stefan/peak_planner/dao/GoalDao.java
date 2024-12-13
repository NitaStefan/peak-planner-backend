package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalDao extends JpaRepository<Goal, Integer> {
}
