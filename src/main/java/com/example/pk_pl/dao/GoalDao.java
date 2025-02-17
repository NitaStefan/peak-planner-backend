package com.example.pk_pl.dao;

import com.example.pk_pl.model.Goal;
import com.example.pk_pl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalDao extends JpaRepository<Goal, Integer> {
    List<Goal> findByUser(User user);
}
