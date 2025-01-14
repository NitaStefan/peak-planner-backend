package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.PlannedEventDao;
import com.stefan.peak_planner.model.PlannedEvent;
import com.stefan.peak_planner.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlannedEventService {

    private final PlannedEventDao plannedEventDao;

    public PlannedEventService(PlannedEventDao plannedEventDao) {
        this.plannedEventDao = plannedEventDao;
    }

    @Transactional
    public PlannedEvent addPlannedEvent(PlannedEvent plannedEvent) {

        return plannedEventDao.save(plannedEvent);
    }

    public List<PlannedEvent> getPlannedEvents(User currentUser) {
        System.out.println(currentUser);

        return plannedEventDao.findByUser(currentUser);
    }
}
