package com.example.pk_pl.service;

import com.example.pk_pl.dao.FlexibleEventDao;
import com.example.pk_pl.model.FlexibleEvent;
import com.example.pk_pl.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlexibleEventServiceTest {
    @Mock
    private FlexibleEventDao dao;
    @InjectMocks
    private FlexibleEventService service;
    @Test
    void getUpcomingCallsDao() {
        User user = new User();
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