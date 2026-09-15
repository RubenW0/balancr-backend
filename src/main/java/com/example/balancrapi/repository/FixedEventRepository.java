package com.example.balancrapi.repository;

import com.example.balancrapi.model.FixedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access for FixedEvent. Spring Data derives the query from the method
 * name - no custom implementation needed.
 */
public interface FixedEventRepository extends JpaRepository<FixedEvent, Long> {

    List<FixedEvent> findByUserId(Long userId);
}
