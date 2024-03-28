package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.TimeTracking;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTrackingRepository {
    TimeTracking save(TimeTracking timeTracking);
}
