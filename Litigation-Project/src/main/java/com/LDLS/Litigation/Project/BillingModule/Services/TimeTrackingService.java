package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Controllers.CustomExeption;
import com.LDLS.Litigation.Project.BillingModule.Entities.Case;
import com.LDLS.Litigation.Project.BillingModule.Entities.Staff;
import com.LDLS.Litigation.Project.BillingModule.Entities.TimeTracking;
import com.LDLS.Litigation.Project.BillingModule.Repositories.CaseRepository;
import com.LDLS.Litigation.Project.BillingModule.Repositories.StaffRepository;
import com.LDLS.Litigation.Project.BillingModule.Repositories.TimeTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeTrackingService {

    @Autowired
    private TimeTrackingRepository timeTrackingRepository;

    @Autowired
    private StaffRepository staffRepository; // Assuming StaffRepository exists

    @Autowired
    private CaseRepository caseRepository; // Assuming CaseRepository exists

    public TimeTracking createTimeTracking(TimeTracking timeTracking) throws CustomExeption.StaffNotFoundException, CustomExeption.CaseNotFoundException {
        // Validate Staff and Case references
        Staff staff = staffRepository.findById(timeTracking.getStaff().getId())
                .orElseThrow(() -> new CustomExeption.StaffNotFoundException("Staff with EMAIL " + timeTracking.getStaff().getEmail() + " not found"));
        Case theCase = caseRepository.findById(timeTracking.getTheCase().getId())
                .orElseThrow(() -> new CustomExeption.CaseNotFoundException("Case with ID " + timeTracking.getTheCase().getId() + " not found"));

// ...
        // Set references to avoid redundant saves
        timeTracking.setStaff(staff);
        timeTracking.setTheCase(theCase);

        return timeTrackingRepository.save(timeTracking);
    }

    // Additional methods for retrieving, updating, or deleting TimeTracking entries can be added here
}