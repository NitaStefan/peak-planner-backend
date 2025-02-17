package com.example.pk_pl.dao;

import com.example.pk_pl.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao extends JpaRepository<Activity, Integer> {
}
