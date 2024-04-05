package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Entities.PaymentMethod;
import com.LDLS.Litigation.Project.BillingModule.Repositories.PaymentMethodRepository;
import com.LDLS.Litigation.Project.BillingModule.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private CrudRepository invoiceRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod findPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PaymentMethod not found with id: " + id));
    }


    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/processPayment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        try {
            Long invoiceId = paymentRequestDto.getInvoiceId();
            PaymentMethod paymentMethod = paymentRequestDto.getPaymentMethod();

            Invoice invoice = (Invoice) invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new CustomExeption.ResourceNotFoundException("Invoice not found"));

            paymentService.processPayment(invoice, paymentMethod);

            return ResponseEntity.ok("Payment processed successfully");
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment: " + e.getMessage());
        }
    }
}