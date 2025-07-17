package com.example.pk_pl.service;

import com.example.pk_pl.dao.GoalDao;
import com.example.pk_pl.dao.StepDao;
import com.example.pk_pl.model.Goal;
import com.example.pk_pl.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GoalServiceTest {
    @Mock
    private GoalDao goalDao;
    @Mock
    private StepDao stepDao;
    @InjectMocks
    private GoalService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addStepToGoalAddsAndReorders() {
        Goal goal = new Goal();
        List<Step> steps = new ArrayList<>();
        Step s1 = new Step(); s1.setOrderIndex(1); steps.add(s1);
        Step s2 = new Step(); s2.setOrderIndex(2); steps.add(s2);
        goal.setSteps(steps);
        when(goalDao.findById(1)).thenReturn(Optional.of(goal));
        Step newStep = new Step(); newStep.setOrderIndex(1);
        when(stepDao.save(any(Step.class))).thenReturn(newStep);
        Step result = service.addStepToGoal(1, newStep);
        verify(stepDao).saveAll(steps);
        assertEquals(1, result.getOrderIndex());
    }

    @Test
    void deleteStepReordersRemaining() {
        Goal goal = new Goal();
        Step s1 = new Step(); s1.setId(1); s1.setOrderIndex(1);
        Step s2 = new Step(); s2.setId(2); s2.setOrderIndex(2);
        List<Step> steps = new ArrayList<>(); steps.add(s1); steps.add(s2);
        goal.setSteps(steps);
        s1.setGoal(goal); s2.setGoal(goal);
        when(stepDao.findById(2)).thenReturn(Optional.of(s2));
        service.deleteStep(2);
        verify(stepDao).saveAll(anyList());
        verify(stepDao).delete(s2);
        assertEquals(1, steps.get(0).getOrderIndex());
    }
}
