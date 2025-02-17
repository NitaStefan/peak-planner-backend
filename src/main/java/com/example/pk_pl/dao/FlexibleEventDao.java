package com.example.pk_pl.dao;


import com.example.pk_pl.model.FlexibleEvent;
import com.example.pk_pl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface FlexibleEventDao extends JpaRepository<FlexibleEvent, Integer> {

    List<FlexibleEvent> findByUserOrderByStartDate(User user);

    List<FlexibleEvent> findByUserAndEndDateAfterOrderByStartDateAsc(User user, Instant theDayBefore);

    List<FlexibleEvent> findByUserAndEndDateBeforeOrderByEndDateDesc(User user, Instant theDayBefore);
}
