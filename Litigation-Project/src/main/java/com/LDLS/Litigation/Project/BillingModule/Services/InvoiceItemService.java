//package com.LDLS.Litigation.Project.BillingModule.Services;
//
//import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
//import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceItem;
//import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceItemRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class InvoiceItemService {
//
//    private final InvoiceItemRepository invoiceItemRepository;
//
//    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository) {
//        this.invoiceItemRepository = invoiceItemRepository;
//    }
//    public InvoiceItem createInvoiceItem(Long invoiceId, InvoiceItem item) {
//        InvoiceItem invoiceItem = new InvoiceItem(); // Create a new InvoiceItem object
//        Invoice invoice = new Invoice();
//        item.setInvoice(invoice);
//        return invoiceItemRepository.save(item);
//    }
//
//    // ... other methods for managing invoice items
//}
