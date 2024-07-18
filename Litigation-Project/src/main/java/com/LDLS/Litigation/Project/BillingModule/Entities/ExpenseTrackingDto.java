package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.Data;

import java.time.LocalDate;


@Data
public class ExpenseTrackingDto {
    private String firstName;
    private String lastName;
    private String expenseType;
    private String expenseId;
    private String description;
    private Double amount;
    private LocalDate expenseDate;
}
