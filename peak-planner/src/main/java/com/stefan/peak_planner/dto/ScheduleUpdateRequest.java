package com.stefan.peak_planner.dto;

import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.model.WeekDay;

import java.util.List;
import java.util.Map;

public class ScheduleUpdateRequest {
    private List<Integer> idsToDelete;
    private Map<WeekDay, List<Activity>> activitiesToAdd;

    public List<Integer> getIdsToDelete() {
        return idsToDelete;
    }

    public void setIdsToDelete(List<Integer> idsToDelete) {
        this.idsToDelete = idsToDelete;
    }

    public Map<WeekDay, List<Activity>> getActivitiesToAdd() {
        return activitiesToAdd;
    }

    public void setActivitiesToAdd(Map<WeekDay, List<Activity>> activitiesToAdd) {
        this.activitiesToAdd = activitiesToAdd;
    }
}

