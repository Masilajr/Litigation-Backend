package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    private Case theCase; // Foreign key referencing Case entity

  @NotNull
    private String type; // Expense category (e.g., Court Fees, Travel)
    @NotNull
    private String description; // Details of the expense
    @NotNull
    private Double amount;

    // Getters and Setters omitted for brevity
}
