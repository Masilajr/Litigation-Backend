package com.LDLS.Litigation.Project.BillingModule.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@JsonIgnoreProperties({"billingReport"})
public class InvoiceReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Double invoiceAmount;
    private String paymentStatus;
    private String invoiceNumber;
    private String contactInformation;
    private LocalDate invoiceDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate billingPeriodFrom;
    private LocalDate billingPeriodTo;
    private String customerName;
    private String customerAddress;
    private LocalDate dueDate;
//    private Double latePaymentFees;
//      private String returnPolicy;

    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "billing_report_id")
    @JsonIgnore
    private BillingReport billingReport;

    public void generateInvoiceNumber() {
        String statusPrefix = paymentStatus.length() > 3 ? paymentStatus.substring(0, 3) : paymentStatus;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formattedDate = invoiceDate.format(formatter);

        this.invoiceNumber = statusPrefix + formattedDate;
    }

    public enum PaymentMethod {
        CASH("CASH"),
        CHEQUE("CHEQUE"),
        BANK_TRANSFER("BANK_TRANSFER"),
        CREDIT_CARD( "CREDIT_CARD");

        private String value;

        PaymentMethod(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static PaymentMethod fromValue(String value) {
            for (PaymentMethod paymentMethod : PaymentMethod.values()) {
                if (paymentMethod.getValue().equals(value)) {
                    return paymentMethod;
                }
            }
            throw new IllegalArgumentException("Invalid payment method: " + value);
        }
    }
}
