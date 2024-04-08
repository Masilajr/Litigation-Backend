package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Entities.PaymentMethod;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private InvoiceRepository invoiceRepository;

    public void processPayment(Invoice invoice, PaymentMethod paymentMethod) {
            switch (paymentMethod) {
                case CASH:
                    processCashPayment(invoice);
                    break;
                case BANK_TRANSFER:
                    processBankTransferPayment(invoice);
                    break;
                case CHEQUE:
                    processChequePayment(invoice);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
            }
        }

    private void processPayment(Invoice invoice) {
        if (invoice.getPaymentMethod().equals("cheque")) {
            processChequePayment(invoice);
        } else if (invoice.getPaymentMethod().equals("cash")) {
            processCashPayment(invoice);
        } else {
            throw new IllegalArgumentException("Unsupported payment method: " + invoice.getPaymentMethod());
        }
    }
    private void processChequePayment(Invoice invoice) {
        // Implementation for processing credit card payment
        // This could involve calling an external payment gateway API
        System.out.println("Processing cheque payment for invoice: " + invoice.getId());
    }

    private void processCashPayment(Invoice invoice) {
        invoice.markAsPaid();
        invoiceRepository.save(invoice);
    }
    private void processBankTransferPayment(Invoice invoice) {
            boolean transferSuccessful = initiateBankTransfer(invoice);
            if (transferSuccessful) {
                invoice.markAsPaid();
                invoiceRepository.save(invoice);
            } else {
                System.out.println("Bank transfer failed for invoice: " + invoice.getId());
            }
        }

    private boolean initiateBankTransfer(Invoice invoice) {
        String bankTransferApiUrl = "https://example.com/bank-transfer";

        WebClient webClient = WebClient.create();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("invoiceId", invoice.getId());
        requestBody.put("amount", invoice.getTotalAmount());

        return Boolean.TRUE.equals(webClient.post()
                .uri(bankTransferApiUrl)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> response.contains("success"))
                .onErrorReturn(false).block());
    }
    }


