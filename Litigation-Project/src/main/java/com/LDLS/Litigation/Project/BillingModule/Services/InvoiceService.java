package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Controllers.CustomExeption;
import com.LDLS.Litigation.Project.BillingModule.Entities.Client;
import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Entities.PaymentMethod;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceRepository;
import org.aspectj.bridge.ISourceLocation;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private MessageUtil log;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) obj;
        return Objects.equals(id, invoice.getId());
    }


    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice findById(Long InvoiceId) {
        return invoiceRepository.findById(InvoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + InvoiceId + " not found"));
    }


    @Autowired
    private PaymentService paymentService;

    public void handleInvoicePayment(Invoice invoice, PaymentMethod paymentMethod) {
        paymentService.processPayment(invoice, paymentMethod);
    }
//    @Autowired
//    public void PaymentService(InvoiceRepository invoiceRepository) {
//        this.invoiceRepository = invoiceRepository;
//    }

    public Invoice save(Invoice invoice) {
        try {
            Invoice item = new Invoice();
            invoice.add(item);
            return invoiceRepository.save(invoice);
        } catch (Exception e) {
            log.error("Error saving invoice: ", (ISourceLocation) e);
            throw new RuntimeException("Error saving invoice", e);
        }
    }


    public Invoice generateInvoice(Long clientId, List<Invoice> items) {
        ClientService clientService = new ClientService();
        Client client = clientService.findClientById(clientId);
        Invoice invoice = new Invoice();
        invoice.setClient(client);
        invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString()); // Generate unique invoice number
        invoice.setInvoiceDate(new Date());
        invoice.set(items);
        invoice.setTotalAmount(items.stream().map((Invoice items1) -> Invoice.getTotal(items1)).reduce(BigDecimal.ZERO, BigDecimal::add));
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> findInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoiceItem(Long invoiceId, Invoice item) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new CustomExeption.ResourceNotFoundException("Invoice not found"));
        item.setInvoice(invoice);
        invoice.getItems().add(item); // Assuming getItems() returns the list of InvoiceItems
        return invoiceRepository.save(invoice);
    }


    public Invoice updateInvoice(Long id, Invoice invoice) {
        Optional<Invoice> existingInvoice = invoiceRepository.findById(id);
        if (!existingInvoice.isPresent()) {
            throw new IllegalArgumentException("Invoice with ID " + id + " not found");
        }
        invoice.setId(id);

        return invoiceRepository.save(invoice);
    }
}

