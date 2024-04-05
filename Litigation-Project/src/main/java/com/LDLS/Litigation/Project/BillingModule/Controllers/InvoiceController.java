package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceItem;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceRepository;
import com.LDLS.Litigation.Project.BillingModule.Services.InvoiceItemService;
import com.LDLS.Litigation.Project.BillingModule.Services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceItemService invoiceService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    //    @PostMapping
//    public ResponseEntity<Invoice> createInvoice(@RequestBody List<InvoiceItem> invoiceItems) {
//        Long clientId = // Extract client ID from request body or header (implementation specific)
//                Invoice invoice = invoiceService.createInvoice(clientId, invoiceItems);
//        return ResponseEntity.ok(invoice);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        InvoiceService invoiceService = new InvoiceService(invoiceRepository);
        Optional<Invoice> invoiceOptional = invoiceService.findInvoiceById(id);
        return invoiceOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        Long invoiceId = invoice.getId();
        if (invoiceId== null){
            throw new IllegalArgumentException("Invoice ID cannot be null");
        }
        InvoiceItem item = new InvoiceItem();
        InvoiceItem createdItem = invoiceService.createInvoiceItem(invoiceId, item);
        return invoice;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return invoice;
    }
}
