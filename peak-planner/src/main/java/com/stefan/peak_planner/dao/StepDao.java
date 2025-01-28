package com.stefan.peak_planner.dao;

import com.stefan.peak_planner.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepDao extends JpaRepository<Step, Integer> {
}
