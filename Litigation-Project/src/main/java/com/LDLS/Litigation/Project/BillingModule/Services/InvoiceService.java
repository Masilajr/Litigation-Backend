package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Controllers.CustomExeption;
import com.LDLS.Litigation.Project.BillingModule.Entities.Client;
import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceItem;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ClientService clientService; // Assuming ClientService for client details

    public Invoice createInvoice(Long clientId, List<InvoiceItem> items) {
        Client client = clientService.findClientById(clientId);
        Invoice invoice = new Invoice();
        invoice.setClient(client);
        invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString()); // Generate unique invoice number
        invoice.setInvoiceDate(new Date());
        invoice.setItems(items);
        invoice.setTotalAmount(items.stream().map(InvoiceItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> findInvoiceById(Long id) {
        Invoice invoice = new Invoice(); // Your logic to find the Invoice by id
        if (invoice != null) {
            return Optional.of(invoice);
        } else {
            return Optional.empty();
        }
    }
    public InvoiceItem createInvoiceItem(Long invoiceId, InvoiceItem item) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new CustomExeption.ResourceNotFoundException("Invoice not found"));
        item.setInvoice(invoice); // Set invoice reference
        return invoiceRepository.save(invoice).getItems().add(item) ? item : null;
    }
    // ... other invoice management methods
}
