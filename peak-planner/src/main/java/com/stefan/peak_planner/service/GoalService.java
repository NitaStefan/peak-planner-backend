package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.GoalDao;
import com.stefan.peak_planner.model.Goal;
import com.stefan.peak_planner.model.UserOwned;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoalService {

    private final GoalDao goalDao;

    public GoalService(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    @Transactional
    public Goal addGoal(Goal goal) {

        return goalDao.save(goal);
    }
}
