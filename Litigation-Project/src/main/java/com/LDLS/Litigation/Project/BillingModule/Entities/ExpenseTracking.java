package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Case theCase; // Foreign key referencing Case entity

    private String type; // Expense category (e.g., Court Fees, Travel)
    private String description; // Details of the expense
    private BigDecimal amount;

    // Getters and Setters omitted for brevity
}
