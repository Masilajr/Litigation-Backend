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
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Invoice invoice;

    private String description; // Description of the service or expense
    private BigDecimal quantity; // Quantity of the item (e.g., hours for time-based fees)
    private BigDecimal rate; // Hourly rate or flat fee

    public BigDecimal getTotal() {
        return quantity.multiply(rate);
    }

    // Getters and Setters omitted for brevity
}

