package com.example.pk_pl.dao;

import com.example.pk_pl.model.DayOfWeek;
import com.example.pk_pl.model.User;
import com.example.pk_pl.model.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayOfWeekDao extends JpaRepository<DayOfWeek, Integer> {
    Optional<DayOfWeek> findByUserAndDay(User user, WeekDay day);

    List<DayOfWeek> findByUser(User user);
}
