package com.example.pk_pl.service;

import com.example.pk_pl.dao.ActivityDao;
import com.example.pk_pl.dao.DayOfWeekDao;
import com.example.pk_pl.dao.GoalDao;
import com.example.pk_pl.dto.ScheduleUpdateRequest;
import com.example.pk_pl.model.Activity;
import com.example.pk_pl.model.DayOfWeek;
import com.example.pk_pl.model.User;
import com.example.pk_pl.model.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DayOfWeekServiceTest {
    @Mock
    private DayOfWeekDao dayOfWeekDao;
    @Mock
    private ActivityDao activityDao;
    @Mock
    private GoalDao goalDao;
    @InjectMocks
    private DayOfWeekService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDaysOfWeekCreatesSevenEntries() {
        User user = new User();
        service.createDaysOfWeek(user);
        verify(dayOfWeekDao).saveAll(argThat(list -> list.size() == 7));
    }

    @Test
    void getDayOfWeekNotFoundThrows() {
        when(dayOfWeekDao.findByUserAndDay(any(), any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getDayOfWeek(new User(), WeekDay.MONDAY));
    }

    @Test
    void updateScheduleDeletesAndAddsActivities() {
        ScheduleUpdateRequest req = new ScheduleUpdateRequest();
        req.setIdsToDelete(List.of(1));
        Map<WeekDay,List<Activity>> toAdd = new EnumMap<>(WeekDay.class);
        toAdd.put(WeekDay.MONDAY, List.of(new Activity()));
        req.setActivitiesToAdd(toAdd);
        DayOfWeek day = new DayOfWeek();
        when(dayOfWeekDao.findByUserAndDay(any(), eq(WeekDay.MONDAY))).thenReturn(Optional.of(day));
        when(dayOfWeekDao.findAll()).thenReturn(List.of(day));
        List<DayOfWeek> result = service.updateSchedule(new User(), req);
        verify(activityDao).deleteAllById(req.getIdsToDelete());
        verify(activityDao).saveAll(anyList());
        assertEquals(1, result.size());
    }
}
