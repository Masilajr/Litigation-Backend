package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.TimeTracking;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTrackingRepository extends JpaRepository<TimeTracking, Long> {
    @NotNull
    TimeTracking save(@NotNull TimeTracking timeTracking);
}
