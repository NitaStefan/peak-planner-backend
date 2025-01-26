package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.EventDetailsDao;
import com.stefan.peak_planner.dao.PlannedEventDao;
import com.stefan.peak_planner.exception.ResourceNotFoundException;
import com.stefan.peak_planner.model.EventDetails;
import com.stefan.peak_planner.model.PlannedEvent;
import com.stefan.peak_planner.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlannedEventService {

    private final PlannedEventDao plannedEventDao;

    private final EventDetailsDao eventDetailsDao;

    public PlannedEventService(PlannedEventDao plannedEventDao, EventDetailsDao eventDetailsDao) {
        this.plannedEventDao = plannedEventDao;
        this.eventDetailsDao = eventDetailsDao;
    }

    public List<PlannedEvent> getPlannedEvents(User currentUser) {

        return plannedEventDao.findByUserOrderByScheduledDate(currentUser);
    }

    @Transactional
    public PlannedEvent savePlannedEvent(PlannedEvent plannedEvent) {

        for (EventDetails eventDetails: plannedEvent.getEventDetails())
            eventDetails.setPlannedEvent(plannedEvent);

        return plannedEventDao.save(plannedEvent);
    }

    @Transactional
    public void deletePlannedEvent(int plannedEventId) {

        plannedEventDao.deleteById(plannedEventId);
    }

    @Transactional
    public void deleteEventDetails(List<Integer> eventDetailIds) {

        for (Integer id : eventDetailIds)  eventDetailsDao.deleteById(id);
    }
}
