package com.LDLS.Litigation.Project.diary.repository;

import com.LDLS.Litigation.Project.diary.model.Events;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Events, Long> {
    List<Events> findAllByShortTitle(String title);
    @Query("SELECT e FROM Events e WHERE e.eventDate >= :today")
    List<Events> findAllByEventDateGreaterThanEqual(LocalDate today);

}
