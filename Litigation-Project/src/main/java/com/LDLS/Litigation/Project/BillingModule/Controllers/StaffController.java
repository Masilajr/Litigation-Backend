package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Staff;
import com.LDLS.Litigation.Project.BillingModule.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff") // Assuming a base path for API endpoints
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping
    public ResponseEntity<Staff> createStaff(@Valid @RequestBody Staff staff) {
        Staff createdStaff = staffService.createStaff(staff);
        return ResponseEntity.ok(createdStaff);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Optional<Staff> staff = staffService.getStaffById(id);
        if (staff.isPresent()) {
            return ResponseEntity.ok(staff.get());
        } else {
            return ResponseEntity.notFound().build(); // Handle staff not found
        }
    }

    // Additional methods for retrieving staff by email or other criteria can be added
}