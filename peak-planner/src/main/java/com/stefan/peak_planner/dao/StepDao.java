package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StepDao extends JpaRepository<Step, Integer> {

    @Query("SELECT s FROM Step s WHERE s.goal.id = :goalId ORDER BY s.orderIndex ASC")
    List<Step> findStepsByGoalId(int goalId);
}
