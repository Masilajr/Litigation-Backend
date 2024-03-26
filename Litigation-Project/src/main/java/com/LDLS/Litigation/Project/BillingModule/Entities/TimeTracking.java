//package com.LDLS.Litigation.Project.BillingModule.Entities;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class TimeTracking {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    //private String staffName; // Staff member's name
//
//    @ManyToOne
//    private Case theCase; // Foreign key referencing Case entity
//
//    private Staff member; // Can be a separate entity for staff details
//    private Date date;
//    private BigDecimal hoursSpent;
//    private String description; // Brief description of work performed
//
//    // Getters and Setters omitted for brevity
//    @ManyToOne
//    @JoinColumn(name = "staff_id")
//    private Staff staff;
//
////    @ManyToOne
////    private Staff staff; // Reference to the associated Staff member
//
//    // Getters and Setters omitted for brevity
//
//}
