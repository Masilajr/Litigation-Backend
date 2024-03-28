package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceItem;
import com.LDLS.Litigation.Project.BillingModule.Services.InvoiceItemService;
import com.LDLS.Litigation.Project.BillingModule.Services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceItemService invoiceService;

//    @PostMapping
//    public ResponseEntity<Invoice> createInvoice(@RequestBody List<InvoiceItem> invoiceItems) {
//        Long clientId = // Extract client ID from request body or header (implementation specific)
//                Invoice invoice = invoiceService.createInvoice(clientId, invoiceItems);
//        return ResponseEntity.ok(invoice);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        InvoiceService invoiceService = new InvoiceService();
        Optional<Invoice> invoiceOptional = invoiceService.findInvoiceById(id);
        if (invoiceOptional.isPresent()) {
            return ResponseEntity.ok(invoiceOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @PostMapping("/{invoiceId}/items")
    public ResponseEntity<InvoiceItem> createInvoiceItem(@PathVariable Long invoiceId, @RequestBody InvoiceItem item) {
        InvoiceItem createdItem = invoiceService.createInvoiceItem(invoiceId, item);
        return ResponseEntity.ok(createdItem);
    }

    // ... other invoice controller methods (optional)
}

