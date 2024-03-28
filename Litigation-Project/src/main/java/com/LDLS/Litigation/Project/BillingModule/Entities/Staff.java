package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity( name="staff")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Assuming this is the primary key for Staff

    private String staffName;
    private String email;
    private String phone;

    @ManyToOne // Assuming a many-to-one relationship with TimeTracking
    private Staff assignedStaff; // Staff member assigned to time entries
    // ... other staff details (role, department etc.)

    // Getters and Setters omitted for brevity
//    @OneToMany(mappedBy = "staff") // Assuming a 'staff' field in TimeTracking
//    private List<TimeTracking> timeEntries; // List of time entries for this staff

}
