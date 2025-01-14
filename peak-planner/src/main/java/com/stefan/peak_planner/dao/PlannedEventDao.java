package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.PlannedEvent;
import com.stefan.peak_planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannedEventDao extends JpaRepository<PlannedEvent, Integer> {

    List<PlannedEvent> findByUser(User user);
}
