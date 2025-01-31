package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.Goal;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.projection.GoalProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalDao extends JpaRepository<Goal, Integer> {
    List<GoalProjection> findByUser(User user);
}
