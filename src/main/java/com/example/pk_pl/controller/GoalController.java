package com.example.pk_pl.controller;

import com.example.pk_pl.dto.GoalWithCurrentStepDTO;
import com.example.pk_pl.model.Goal;
import com.example.pk_pl.model.Step;
import com.example.pk_pl.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getGoals() {
        List<Goal> goals = goalService.getGoals(null);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<List<Step>> getStepsByGoalId(@PathVariable int id) {

        return new ResponseEntity<>(goalService.findStepsByGoalId(id), HttpStatus.OK);
    }

    @GetMapping("/with-current-step")
    public ResponseEntity<List<GoalWithCurrentStepDTO>> getGoalsWithCurrentStep() {

        return new ResponseEntity<>(goalService.getGoalsWithCurrentStep(null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal) {
        Goal dbGoal = goalService.saveGoal(goal);
        return new ResponseEntity<>(dbGoal, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Goal> updateGoal(@RequestBody Goal goal) {
        Goal dbGoal = goalService.saveGoal(goal);
        return new ResponseEntity<>(dbGoal, HttpStatus.OK);
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable int goalId) {
        goalService.deleteGoal(goalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{goalId}/steps")
    public ResponseEntity<Step> addStep(@PathVariable int goalId, @RequestBody Step step) {

        Step dbStep = goalService.addStepToGoal(goalId, step);

        return new ResponseEntity<>(dbStep, HttpStatus.CREATED);
    }

    @PutMapping("/steps")
    public ResponseEntity<Step> updateStep(@RequestBody Step step) {
        Step dbStep = goalService.updateStep(step);
        return new ResponseEntity<>(dbStep, HttpStatus.OK);
    }

    @DeleteMapping("/steps/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable int stepId) {
        goalService.deleteStep(stepId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

