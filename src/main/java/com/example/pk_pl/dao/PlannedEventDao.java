package com.example.pk_pl.dao;

import com.example.pk_pl.model.PlannedEvent;
import com.example.pk_pl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlannedEventDao extends JpaRepository<PlannedEvent, Integer> {

    List<PlannedEvent> findByUserOrderByScheduledDate(User user);

    List<PlannedEvent> findByUserAndScheduledDateAfterOrderByScheduledDateAsc(User user, LocalDateTime yesterday);

    List<PlannedEvent> findByUserAndScheduledDateBeforeOrderByScheduledDateDesc(User user, LocalDateTime yesterday);
}
