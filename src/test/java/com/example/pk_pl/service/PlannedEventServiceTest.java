package com.example.pk_pl.service;

import com.example.pk_pl.dao.PlannedEventDao;
import com.example.pk_pl.model.EventDetails;
import com.example.pk_pl.model.PlannedEvent;
import com.example.pk_pl.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlannedEventServiceTest {
    @Mock
    private PlannedEventDao plannedEventDao;
    @InjectMocks
    private PlannedEventService service;

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