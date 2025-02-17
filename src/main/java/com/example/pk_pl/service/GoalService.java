package com.example.pk_pl.service;

import com.example.pk_pl.dao.GoalDao;
import com.example.pk_pl.dao.StepDao;
import com.example.pk_pl.dto.GoalWithCurrentStepDTO;
import com.example.pk_pl.model.Goal;
import com.example.pk_pl.model.Step;
import com.example.pk_pl.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoalService {

    private final GoalDao goalDao;

    private final StepDao stepDao;


    public GoalService(GoalDao goalDao, StepDao stepDao) {
        this.goalDao = goalDao;
        this.stepDao = stepDao;
    }

    public List<Goal> getGoals(User currentUser) {

        return goalDao.findByUser(currentUser);
    }

    public List<Step> findStepsByGoalId(int id) {

        return stepDao.findStepsByGoalId(id);
    }

    @Transactional
    public Goal saveGoal(Goal goal) {

        return goalDao.save(goal);
    }

    @Transactional
    public void deleteGoal(int goalId) {

        goalDao.deleteById(goalId);
    }

    @Transactional
    public Step addStepToGoal(int goalId, Step step) {
        Goal goal = goalDao.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        List<Step> steps = goal.getSteps();

        boolean reordered = false;

        //if orderIndex provided, reorder steps to make room
        if (step.getOrderIndex() != 0 && step.getOrderIndex() <= steps.size()) {
            for (int i = steps.size() - 1; i >= step.getOrderIndex() - 1; i--)
                steps.get(i).setOrderIndex(steps.get(i).getOrderIndex() + 1);
            reordered = true;
        } else step.setOrderIndex(steps.size() + 1);

        step.setGoal(goal);

        if (reordered) stepDao.saveAll(steps);
        return stepDao.save(step);
    }

    @Transactional
    public Step updateStep(Step updatedStep) {
        Step existingStep = stepDao.findById(updatedStep.getId())
                .orElseThrow(() -> new RuntimeException("Step not found"));

        Goal goal = existingStep.getGoal();
        List<Step> steps = goal.getSteps(); // Already ordered by orderIndex

        int oldIndex = existingStep.getOrderIndex() - 1; // Convert to list index (0-based)
        int newIndex = updatedStep.getOrderIndex() - 1; // Convert to list index (0-based)

        if (oldIndex != newIndex) {
            if (newIndex < oldIndex) {
                for (int i = newIndex; i < oldIndex; i++) {
                    steps.get(i).setOrderIndex(steps.get(i).getOrderIndex() + 1);
                }
            } else {
                for (int i = oldIndex + 1; i <= newIndex; i++) {
                    steps.get(i).setOrderIndex(steps.get(i).getOrderIndex() - 1);
                }
            }
        }

        updatedStep.setGoal(goal);

        // Save only the modified steps
        stepDao.saveAll(steps.subList(Math.min(oldIndex, newIndex), Math.max(oldIndex, newIndex) + 1));

        return stepDao.save(updatedStep);
    }

    @Transactional
    public void deleteStep(int stepId) {
        Step stepToDelete = stepDao.findById(stepId)
                .orElseThrow(() -> new RuntimeException("Step not found"));

        Goal goal = stepToDelete.getGoal();
        List<Step> steps = goal.getSteps();

        steps.remove(stepToDelete);

        // Reorder remaining steps
        for (int i = 0; i < steps.size(); i++) {
            steps.get(i).setOrderIndex(i + 1);
        }

        // Save reordered steps and delete the step
        stepDao.saveAll(steps);
        stepDao.delete(stepToDelete);
    }

    public List<GoalWithCurrentStepDTO> getGoalsWithCurrentStep(User currentUser) {

        List<Goal> goals = goalDao.findByUser(currentUser);

        System.out.println(currentUser.getUsername());

        return goals.stream()
                .map(GoalWithCurrentStepDTO::new)
                .toList();
    }
}



