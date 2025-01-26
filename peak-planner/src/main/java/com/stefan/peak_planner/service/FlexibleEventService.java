package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.FlexibleEventDao;
import com.stefan.peak_planner.model.FlexibleEvent;
import com.stefan.peak_planner.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlexibleEventService {

    private final FlexibleEventDao flexibleEventDao;

    public FlexibleEventService(FlexibleEventDao flexibleEventDao) {
        this.flexibleEventDao = flexibleEventDao;
    }

    public List<FlexibleEvent> getFlexibleEvents(User currentUser) {

        return flexibleEventDao.findByUserOrderByStartDate(currentUser);
    }

    @Transactional
    public FlexibleEvent saveFlexibleEvent(FlexibleEvent flexibleEvent) {

        return flexibleEventDao.save(flexibleEvent);
    }
}
