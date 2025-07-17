package com.example.pk_pl.service;

import com.example.pk_pl.dao.EventDetailsDao;
import com.example.pk_pl.dao.PlannedEventDao;
import com.example.pk_pl.model.EventDetails;
import com.example.pk_pl.model.PlannedEvent;
import com.example.pk_pl.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlannedEventServiceTest {
    @Mock
    private PlannedEventDao plannedEventDao;
    @Mock
    private EventDetailsDao eventDetailsDao;
    @InjectMocks
    private PlannedEventService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUpcomingEventsCallsDao() {
        when(plannedEventDao.findByUserAndScheduledDateAfterOrderByScheduledDateAsc(any(), any())).thenReturn(List.of(new PlannedEvent()));
        List<PlannedEvent> res = service.getUpcomingPlannedEvents(new User());
        assertEquals(1, res.size());
    }

    @Test
    void savePlannedEventSetsEventDetails() {
        PlannedEvent event = new PlannedEvent();
        EventDetails details = new EventDetails();
        event.setEventDetails(List.of(details));
        when(plannedEventDao.save(event)).thenReturn(event);
        PlannedEvent saved = service.savePlannedEvent(event);
        assertSame(event, saved);
        assertEquals(event, details.getPlannedEvent());
    }
}
