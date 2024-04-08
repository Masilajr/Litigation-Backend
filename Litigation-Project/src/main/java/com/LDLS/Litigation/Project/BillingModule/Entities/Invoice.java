package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

import static org.apache.commons.math3.util.MathUtils.reduce;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Invoice number cannot be null")
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Date invoiceDate;
    private BigDecimal totalAmount;

    public void setClient(Client client) {
    }

    public static class InvoiceItem {
        private final List<InvoiceItem> items;

        public InvoiceItem() {
            this.items = new ArrayList<>();
        }

        public void addItem(InvoiceItem item) {
            this.items.add(item);
        }

        public List<InvoiceItem> getItems() {
            return this.items;
        }

        public static double getAmount(double v) {
            return v;
        }

        public void setInvoice(Invoice invoice) {
        }

    }
    public static BigDecimal getTotal(Invoice invoice) {
        List<Double> invoiceItemQuantities = Arrays.asList(2.5, 1.0, 3.75);
        BigDecimal totalQuantity = invoiceItemQuantities.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalQuantity;
    }

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items = new ArrayList<>();

    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this);
    }

    public void markAsPaid() {
    }

    public void add(Invoice item) {
    }

    public Invoice get() {
        return null;
    }

    public void setInvoice(Invoice invoice) {
    }

    public DoubleStream stream() {
        return null;
    }
    public void set(List<Invoice> items) {
    }

    public Invoice getItems() {
        return null;
    }

}
