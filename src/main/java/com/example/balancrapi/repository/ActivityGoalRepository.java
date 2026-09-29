package com.example.balancrapi.repository;

import com.example.balancrapi.model.ActivityGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access for ActivityGoal. Spring Data derives the query from the method
 * name - no custom implementation needed.
 */
public interface ActivityGoalRepository extends JpaRepository<ActivityGoal, Long> {

    List<ActivityGoal> findByUserId(Long userId);
}
