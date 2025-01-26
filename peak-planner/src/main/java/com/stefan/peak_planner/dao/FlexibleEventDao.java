package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.FlexibleEvent;
import com.stefan.peak_planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlexibleEventDao extends JpaRepository<FlexibleEvent, Integer> {

    List<FlexibleEvent> findByUserOrderByStartDate(User user);
}
