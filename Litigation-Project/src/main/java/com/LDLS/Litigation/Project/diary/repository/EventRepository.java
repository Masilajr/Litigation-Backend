package com.LDLS.Litigation.Project.diary.repository;

import com.LDLS.Litigation.Project.diary.model.Events;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Events, Long> {
    // Adjusted the return type to List<Events> to match the domain model
    @Query(value = "SELECT * FROM events WHERE event_date >= :date", nativeQuery = true)
    List<Events> findAllByEventDateGreaterThanEqual(@Param("date") LocalDate date);

    List<Events> findAllByShortTitle(String title);
}
