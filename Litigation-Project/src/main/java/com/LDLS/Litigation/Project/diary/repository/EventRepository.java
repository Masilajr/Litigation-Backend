package com.LDLS.Litigation.Project.diary.repository;

import com.LDLS.Litigation.Project.diary.model.Event;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.date >= :date")
    List<Event> findAllByEventDateGreaterThanEqual(LocalDate date);
}