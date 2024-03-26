package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Descriptive name for the fee structure (e.g., Hourly Billing)
    private String description; // Details of the fee structure
    private BigDecimal hourlyRate; // Applicable for hourly billing
    private BigDecimal flatFee; // Applicable for flat fees

    // Getters and Setters omitted for brevity
}


