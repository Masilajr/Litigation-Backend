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

    private String description;
    private BigDecimal quantity;
    private BigDecimal rate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Invoice invoice;

    public BigDecimal getTotal() {
        return quantity.multiply(rate);
    }
}

