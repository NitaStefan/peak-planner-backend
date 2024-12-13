package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.Goal;
import com.stefan.peak_planner.model.UserOwned;
import com.stefan.peak_planner.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/goals")
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal) {

        Goal dbGoal = goalService.addGoal(goal);

        return new ResponseEntity<>(dbGoal, HttpStatus.CREATED);
    }
}
