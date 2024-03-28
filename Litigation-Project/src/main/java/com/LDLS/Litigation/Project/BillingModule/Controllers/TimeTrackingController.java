package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.TimeTracking;
import com.LDLS.Litigation.Project.BillingModule.Services.TimeTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time-tracking") // Assuming a base path for API endpoints
public class TimeTrackingController {

    @Autowired
    private TimeTrackingService timeTrackingService;

    @PostMapping
    public ResponseEntity<TimeTracking> createTimeTracking(@RequestBody TimeTracking timeTracking) throws CustomExeption.StaffNotFoundException, CustomExeption.CaseNotFoundException {
        TimeTracking createdTimeTracking = timeTrackingService.createTimeTracking(timeTracking);
        return ResponseEntity.ok(createdTimeTracking);
    }

    // Additional methods for retrieving, updating, or deleting TimeTracking entries can be added here
}