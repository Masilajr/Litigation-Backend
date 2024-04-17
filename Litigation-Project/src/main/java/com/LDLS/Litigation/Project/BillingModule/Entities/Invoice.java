package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.description;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Double amount;
    private String status;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate billingPeriodStartDate;
    private LocalDate billingPeriodEndDate;
    private Double latePaymentFees;
    private String returnPolicy;

    private PaymentMethod paymentMethod;

    public Object getDescription() { return description;

    }

    public void setDescription(Object description) {
    }


    public enum PaymentMethod {
        CASH, CHEQUE, BANK_TRANSFER
    }
    @ManyToOne
    @JoinColumn(name = "billing_report_id")
    private BillingReport billingReport;


    public void generateInvoiceNumber() {
        String statusPrefix = status.length() > 3 ? status.substring(0, 3) : status;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formattedDate = invoiceDate.format(formatter);

        this.invoiceNumber = statusPrefix + formattedDate;

    }

}