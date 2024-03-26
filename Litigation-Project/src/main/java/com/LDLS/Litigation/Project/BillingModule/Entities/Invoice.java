package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client; // Foreign key referencing Client

    private String invoiceNumber; // Unique identifier for the invoice

    private Date invoiceDate;
    private BigDecimal totalAmount;

    public void setItems(List<InvoiceItem> items) {
    }
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items = new ArrayList<>(); // Initialize an empty list

    // Getters and Setters for other fields

    public List<InvoiceItem> getItems() {
        return items; // Return the modifiable list
    }
}
