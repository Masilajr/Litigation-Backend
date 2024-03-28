package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NonNull
    private String invoiceNumber; // Unique identifier for the invoice
    @NonNull
    private Date invoiceDate;
    @NonNull
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL) // Cascade changes to InvoiceItems
    private List<InvoiceItem> items = new ArrayList<>(); // Initialize an empty list

    public void setItems(List<InvoiceItem> items) {
        this.items = items; // Updated setter for clarity
    }

    public List<InvoiceItem> getItems() {
        return items; // Return the modifiable list
    }
}