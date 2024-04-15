package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public Invoice generateInvoice(@RequestBody Invoice invoice) {

        return invoiceService.generateInvoice(invoice);
    }

    @GetMapping("/")
    public List<Invoice> getAllInvoices() {

        return invoiceService.getAllInvoices();
    }
}
