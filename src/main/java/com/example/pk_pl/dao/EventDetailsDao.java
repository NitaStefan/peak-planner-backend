package com.example.pk_pl.dao;

import com.example.pk_pl.model.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDetailsDao extends JpaRepository<EventDetails, Integer> {
}
