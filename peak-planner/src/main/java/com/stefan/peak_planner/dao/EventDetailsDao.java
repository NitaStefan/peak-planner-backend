package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDetailsDao extends JpaRepository<EventDetails, Integer> {
}
