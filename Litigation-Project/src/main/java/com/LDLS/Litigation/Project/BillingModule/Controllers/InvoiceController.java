package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
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
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoiceDetails) {
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new CustomExeption.ResourceNotFoundException("Invoice not found with id: " + id));

        existingInvoice.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
        existingInvoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
        existingInvoice.setBillingPeriodStartDate(invoiceDetails.getBillingPeriodStartDate());
        existingInvoice.setBillingPeriodEndDate(invoiceDetails.getBillingPeriodEndDate());
        existingInvoice.setLatePaymentFees(invoiceDetails.getLatePaymentFees());
        existingInvoice.setReturnPolicy(invoiceDetails.getReturnPolicy());
        existingInvoice.setAmount(invoiceDetails.getAmount());
        existingInvoice.setStatus(invoiceDetails.getStatus());

        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted Successfully");
    }
}
