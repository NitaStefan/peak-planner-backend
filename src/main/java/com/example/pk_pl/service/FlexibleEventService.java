package com.example.pk_pl.service;

import com.example.pk_pl.dao.FlexibleEventDao;
import com.example.pk_pl.model.FlexibleEvent;
import com.example.pk_pl.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class FlexibleEventService {

    private final FlexibleEventDao flexibleEventDao;

    public FlexibleEventService(FlexibleEventDao flexibleEventDao) {
        this.flexibleEventDao = flexibleEventDao;
    }

    public List<FlexibleEvent> getUpcomingFlexibleEvents(User currentUser) {
        LocalDateTime theDayBefore = LocalDateTime.now(ZoneOffset.UTC).minusDays(1);


        return flexibleEventDao.findByUserAndEndDateAfterOrderByStartDateAsc(currentUser, theDayBefore);
    }

    public List<FlexibleEvent> getPastFlexibleEvents(User currentUser) {
        LocalDateTime theDayBefore = LocalDateTime.now(ZoneOffset.UTC).minusDays(1);


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
