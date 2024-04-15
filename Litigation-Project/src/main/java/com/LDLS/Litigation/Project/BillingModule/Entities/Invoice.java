package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Double amount;
    private String status; // e.g., "PENDING", "PAID"

    private PaymentMethod paymentMethod;

    // Constructors, getters, and setters

    public enum PaymentMethod {
        CASH, CHEQUE, BANK_TRANSFER
    }
}