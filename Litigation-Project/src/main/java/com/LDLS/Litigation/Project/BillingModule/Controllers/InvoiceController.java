package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Repositories.ExpenseTrackingRepository;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceRepository;
import com.LDLS.Litigation.Project.BillingModule.Services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping
    public Invoice generateInvoice(@RequestBody Invoice invoice) {

        return invoiceService.generateInvoice(invoice);
    }

    @GetMapping("/")
    public List<Invoice> getAllInvoices() {

        return invoiceService.getAllInvoices();
    }
    @PutMapping("/invoices/{id}")
    public Invoice updateInvoice(Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(invoice.getId()).orElse(null);
        if (existingInvoice == null) {
            return null;
        }
        existingInvoice.setStatus((String) invoice.getStatus());
        existingInvoice.setAmount(invoice.getAmount());
        existingInvoice.setPaymentMethod(invoice.getPaymentMethod());
        Invoice savedInvoice = invoiceRepository.save(existingInvoice);

        return savedInvoice;
    }
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted Successfully");
    }
}
