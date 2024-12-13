package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.PlannedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedEventDao extends JpaRepository<PlannedEvent, Integer> {
}
