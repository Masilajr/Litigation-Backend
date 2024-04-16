package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.description;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Double amount;
    private String status; // e.g., "PENDING", "PAID"

    private PaymentMethod paymentMethod;

    public Object getDescription() { return description;
    }

    public void setDescription(Object description) {
    }

    public enum PaymentMethod {
        CASH, CHEQUE, BANK_TRANSFER
    }
}