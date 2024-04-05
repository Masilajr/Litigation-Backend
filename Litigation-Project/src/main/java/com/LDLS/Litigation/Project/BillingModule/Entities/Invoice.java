package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "client_id") // Assuming 'client_id' is the foreign key column in the database
    private Client client; // Foreign key referencing Client

    @NotBlank(message = "Invoice number cannot be blank")
    private String invoiceNumber; // Unique identifier for the invoice
    private Date invoiceDate;
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL) // Cascade changes to InvoiceItems
    private List<InvoiceItem> items = new ArrayList<>(); // Initialize an empty list

    // Lombok @Data generates getters and setters, so no need to manually define them unless you want to customize them
    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this); // Ensure the InvoiceItem is aware of its parent Invoice
    }

    public void markAsPaid() {
    }
}
