package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.Staff;
import com.LDLS.Litigation.Project.BillingModule.Repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    public Optional<Staff> getStaffByEmail(String email) {
        return Optional.ofNullable(staffRepository.findByEmail(email)); // Assuming findByEmail exists
    }

    // Additional methods for updating, deleting, or finding staff by other criteria can be added
}