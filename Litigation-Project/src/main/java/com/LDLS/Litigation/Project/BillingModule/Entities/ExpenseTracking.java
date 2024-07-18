package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String expenseType;
    private String customerName;
    private String expenseId;
    private String expenseDescription;
    @Column(nullable = false)
    private Double expenseAmount;
    private LocalDate expenseDate;

}
