package com.thkoeln.referee.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.thkoeln.referee.models.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
    List<Event> findByHome(@Param("home") String home);
    List<Event> findByRefIsNull();
}
