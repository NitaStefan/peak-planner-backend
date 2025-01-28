package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.GoalDao;
import com.stefan.peak_planner.dao.StepDao;
import com.stefan.peak_planner.model.Goal;
import com.stefan.peak_planner.model.Step;
import com.stefan.peak_planner.model.User;
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

    @Transactional
    public Goal saveGoal(Goal goal) {

        return goalDao.save(goal);
    }

    public List<Goal> getGoals(User currentUser) {

        return goalDao.findByUser(currentUser);
    }

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
        }
        else step.setOrderIndex(steps.size() + 1);

        step.setGoal(goal);

        if(reordered) stepDao.saveAll(steps);
        return stepDao.save(step);
    }

    public void deleteGoal(int goalId) {

        goalDao.deleteById(goalId);
    }

//    public Step updateStep(Step updatedStep) {
//        // Fetch the existing step using the ID from updatedStep
//        Step existingStep = stepDao.findById(updatedStep.getId())
//                .orElseThrow(() -> new RuntimeException("Step not found"));
//
//        Goal goal = existingStep.getGoal();
//        List<Step> steps = goal.getSteps();
//
//        // If orderIndex has changed, reorder the steps
//        if (updatedStep.getOrderIndex() != existingStep.getOrderIndex()) {
//            steps.remove(existingStep);
//
//            // Adjust positions for the new orderIndex
//            if (updatedStep.getOrderIndex() <= steps.size()) {
//                for (int i = steps.size() - 1; i >= updatedStep.getOrderIndex() - 1; i--) {
//                    steps.get(i).setOrderIndex(steps.get(i).getOrderIndex() + 1);
//                }
//            }
//
//            updatedStep.setOrderIndex(Math.min(updatedStep.getOrderIndex(), steps.size() + 1));
//            steps.add(updatedStep.getOrderIndex() - 1, updatedStep);
//        }
//
//        // Update the other fields of the step
//        existingStep.setTitle(updatedStep.getTitle());
//        existingStep.setDescription(updatedStep.getDescription());
//        existingStep.setDays(updatedStep.getDays());
//        existingStep.setOrderIndex(updatedStep.getOrderIndex());
//
//        // Save the reordered steps and the updated step
//        stepDao.saveAll(steps);
//        return stepDao.save(existingStep);

    //    public void deleteStep(int stepId) {
//        Step stepToDelete = stepRepository.findById(stepId)
//                .orElseThrow(() -> new RuntimeException("Step not found"));
//
//        Goal goal = stepToDelete.getGoal();
//        List<Step> steps = goal.getSteps();
//
//        steps.remove(stepToDelete);
//
//        // Reorder remaining steps
//        for (int i = 0; i < steps.size(); i++) {
//            steps.get(i).setOrderIndex(i + 1);
//        }
//
//        // Save reordered steps and delete the step
//        stepRepository.saveAll(steps);
//        stepRepository.delete(stepToDelete);
//    }
    }



