package com.example.pk_pl.service;

import com.example.pk_pl.dao.EventDetailsDao;
import com.example.pk_pl.dao.PlannedEventDao;
import com.example.pk_pl.model.EventDetails;
import com.example.pk_pl.model.PlannedEvent;
import com.example.pk_pl.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PlannedEventService {

    private final PlannedEventDao plannedEventDao;

    private final EventDetailsDao eventDetailsDao;

    public PlannedEventService(PlannedEventDao plannedEventDao, EventDetailsDao eventDetailsDao) {
        this.plannedEventDao = plannedEventDao;
        this.eventDetailsDao = eventDetailsDao;
    }

//    public List<PlannedEvent> getPlannedEvents(User currentUser) {
//
//        return plannedEventDao.findByUserOrderByScheduledDate(currentUser);
//    }

    public List<PlannedEvent> getUpcomingPlannedEvents(User currentUser) {
        Instant theDayBefore = Instant.now().minus(1, ChronoUnit.DAYS); // include today

        System.out.println(currentUser.getUsername());

        return plannedEventDao.findByUserAndScheduledDateAfterOrderByScheduledDateAsc(currentUser, theDayBefore);
    }

    public List<PlannedEvent> getPastPlannedEvents(User currentUser) {
        Instant theDayBefore = Instant.now().minus(1, ChronoUnit.DAYS);

        return plannedEventDao.findByUserAndScheduledDateBeforeOrderByScheduledDateDesc(currentUser, theDayBefore);
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
}
