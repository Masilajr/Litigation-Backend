package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.TimeTracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class TimeTrackingRepositoryImpl implements TimeTrackingRepository {

    @Autowired
    private EntityManager entityManager; // Assuming JPA persistence

    @Override
    public TimeTracking save(TimeTracking timeTracking) {
        return timeTracking;
    }

    // Additional methods for finding, updating, or deleting TimeTracking entries can be added
}
