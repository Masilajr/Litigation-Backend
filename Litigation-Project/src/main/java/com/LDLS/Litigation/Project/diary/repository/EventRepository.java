package com.LDLS.Litigation.Project.diary.repository;

import com.LDLS.Litigation.Project.diary.model.Events;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Events, Long> {
    List<Events> findAllByShortTitle(String title);
    List<Events> findAllByEventDateGreaterThanEqual(LocalDate today);
    long countByEventDateGreaterThanEqualAndCancelledFalse(LocalDate date);
    long countByCancelledTrue();
    long countByEventDateLessThanEqualAndCancelledFalse(LocalDate date);
    List<Events> findAllByEventDateGreaterThanEqualAndCancelledFalse(LocalDate date);
    List<Events> findAllByEventDateEqualsAndCancelledFalse(LocalDate date);
    List<Events> findAllByEventDateLessThanAndCancelledFalse(LocalDate date);
}
