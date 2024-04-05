package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Controllers.CustomExeption;
import com.LDLS.Litigation.Project.BillingModule.Entities.Client;
import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceItem;
import com.LDLS.Litigation.Project.BillingModule.Entities.PaymentMethod;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Autowired
    private PaymentService paymentService;

    public void handleInvoicePayment(Invoice invoice, PaymentMethod paymentMethod) {
        paymentService.processPayment(invoice, paymentMethod);
    }

    public Invoice save(Invoice invoice) {
        InvoiceItem item = new InvoiceItem();
        invoice.addItem(item);
        return invoiceRepository.save(invoice);
    }

    public Invoice createInvoice(Long clientId, List<InvoiceItem> items) {
        ClientService clientService = new ClientService();
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
        return invoiceRepository.findById(id);
    }

    public InvoiceItem createInvoiceItem(Long invoiceId, InvoiceItem item) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new CustomExeption.ResourceNotFoundException("Invoice not found"));
        item.setInvoice(invoice);
        invoice.getItems().add(item);
        return invoiceRepository.save(invoice).getItems().stream().filter(i -> i.equals(item)).findFirst().orElse(null);
    }
}
