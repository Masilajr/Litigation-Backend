package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceReports;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceReportsRepository;
import com.LDLS.Litigation.Project.BillingModule.Services.InvoiceReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
<<<<<<< HEAD
@RequestMapping("/api/invoices/reports")
=======
@RequestMapping("/api/invoices")
>>>>>>> fbfd28c261accae1eb312f9b89dd2632e586984f
public class InvoiceReportsController {

    @Autowired
    private InvoiceReportsService invoiceReportsService;

    @Autowired
    private InvoiceReportsRepository invoiceReportsRepository;

    @PostMapping
    public InvoiceReports generateInvoiceReports(@RequestBody InvoiceReports invoiceReports) {

        return invoiceReportsService.generateInvoiceReports(invoiceReports);
    }

    @GetMapping("/")
    public List<InvoiceReports> getAllInvoicesReports() {

        return invoiceReportsService.getAllInvoicesReports();
    }
    @PutMapping("/invoices/{id}")
    public ResponseEntity<InvoiceReports> updateInvoiceReports(@PathVariable Long id, @RequestBody InvoiceReports invoiceDetailsReports) {
        InvoiceReports existingInvoiceReports = invoiceReportsRepository.findById(id).orElseThrow(() -> new CustomExeption.ResourceNotFoundException("InvoiceReports not found with id: " + id));

        existingInvoiceReports.setInvoiceNumber(invoiceDetailsReports.getInvoiceNumber());
        existingInvoiceReports.setInvoiceDate(invoiceDetailsReports.getInvoiceDate());
        existingInvoiceReports.setBillingPeriodFrom(invoiceDetailsReports.getBillingPeriodFrom());
        existingInvoiceReports.setBillingPeriodTo(invoiceDetailsReports.getBillingPeriodTo());
//        existingInvoiceReports.setLatePaymentFees(invoiceDetailsReports.getLatePaymentFees());
//        existingInvoiceReports.setReturnPolicy(invoiceDetailsReports.getReturnPolicy());
        existingInvoiceReports.setInvoiceAmount(invoiceDetailsReports.getInvoiceAmount());
        existingInvoiceReports.setPaymentStatus(invoiceDetailsReports.getPaymentStatus());

        InvoiceReports updatedInvoiceReports = invoiceReportsRepository.save(existingInvoiceReports);
        return ResponseEntity.ok(updatedInvoiceReports);
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<?> deleteInvoiceReports(@PathVariable Long id) {
        invoiceReportsService.deleteInvoiceReports(id);
        return ResponseEntity.ok("InvoiceReports deleted Successfully");
    }
}
