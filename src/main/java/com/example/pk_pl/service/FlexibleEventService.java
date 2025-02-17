package com.example.pk_pl.service;

import com.example.pk_pl.dao.FlexibleEventDao;
import com.example.pk_pl.model.FlexibleEvent;
import com.example.pk_pl.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FlexibleEventService {

    private final FlexibleEventDao flexibleEventDao;

    public FlexibleEventService(FlexibleEventDao flexibleEventDao) {
        this.flexibleEventDao = flexibleEventDao;
    }

//    public List<FlexibleEvent> getFlexibleEvents(User currentUser) {
//
//        return flexibleEventDao.findByUserOrderByStartDate(currentUser);
//    }

    public List<FlexibleEvent> getUpcomingFlexibleEvents(User currentUser) {
        Instant theDayBefore = Instant.now().minus(1, ChronoUnit.DAYS);

        return flexibleEventDao.findByUserAndEndDateAfterOrderByStartDateAsc(currentUser, theDayBefore);
    }

    public List<FlexibleEvent> getPastFlexibleEvents(User currentUser) {
        Instant theDayBefore = Instant.now().minus(1, ChronoUnit.DAYS);

        return flexibleEventDao.findByUserAndEndDateBeforeOrderByEndDateDesc(currentUser, theDayBefore);
    }

    @Transactional
    public FlexibleEvent saveFlexibleEvent(FlexibleEvent flexibleEvent) {

        return flexibleEventDao.save(flexibleEvent);
    }

    @Transactional
    public void deleteFlexibleEvent(int flexibleEventId) {

        flexibleEventDao.deleteById(flexibleEventId);
    }
}
