package com.LDLS.Litigation.Project.BillingModule.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "billing_report_id")
//    @JsonIgnore
//    private BillingReport billingReport;

    private String expenseType;
    private String expenseDescription;
    private Double expenseAmount;
    private LocalDate expenseDate;
    private String expenseApprover;
    private LocalDate expenseApprovalDate;
    private String expenseApprovalStatus;

}
