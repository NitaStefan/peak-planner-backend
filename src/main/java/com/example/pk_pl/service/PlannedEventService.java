package com.example.pk_pl.service;

import com.example.pk_pl.dao.EventDetailsDao;
import com.example.pk_pl.dao.PlannedEventDao;
import com.example.pk_pl.model.EventDetails;
import com.example.pk_pl.model.PlannedEvent;
import com.example.pk_pl.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PlannedEventService {

    private final PlannedEventDao plannedEventDao;


    public PlannedEventService(PlannedEventDao plannedEventDao) {
        this.plannedEventDao = plannedEventDao;
    }


    public List<PlannedEvent> getUpcomingPlannedEvents(User currentUser) {
        LocalDateTime theDayBefore = LocalDateTime.now(ZoneOffset.UTC).minusDays(1);// include today

        System.out.println(currentUser.getUsername());

        return plannedEventDao.findByUserAndScheduledDateAfterOrderByScheduledDateAsc(currentUser, theDayBefore);
    }

    public List<PlannedEvent> getPastPlannedEvents(User currentUser) {
        LocalDateTime theDayBefore = LocalDateTime.now(ZoneOffset.UTC).minusDays(1);// include today

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
