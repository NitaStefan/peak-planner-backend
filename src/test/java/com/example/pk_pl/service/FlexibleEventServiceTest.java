package com.example.pk_pl.service;

import com.example.pk_pl.dao.FlexibleEventDao;
import com.example.pk_pl.model.FlexibleEvent;
import com.example.pk_pl.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FlexibleEventServiceTest {
    @Mock
    private FlexibleEventDao dao;
    @InjectMocks
    private FlexibleEventService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUpcomingCallsDao() {
        User user = new User();
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC).minusDays(1);
        when(dao.findByUserAndEndDateAfterOrderByStartDateAsc(any(), any())).thenReturn(List.of(new FlexibleEvent()));
        List<FlexibleEvent> res = service.getUpcomingFlexibleEvents(user);
        assertEquals(1, res.size());
    }

    @Test
    void saveDelegatesToDao() {
        FlexibleEvent e = new FlexibleEvent();
        when(dao.save(e)).thenReturn(e);
        assertSame(e, service.saveFlexibleEvent(e));
    }
}
