package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.ExpenseTracking;
import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Services.BillingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BillingReportController {

    private final BillingReportService billingService;

    @Autowired
    public BillingReportController(BillingReportService billingService) {
        this.billingService = billingService;
    }

    @GetMapping(value = "/billing/reports", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> generateBillingReport() {
        List<Invoice> invoices = billingService.getAllInvoices();
        List<ExpenseTracking> expenses = billingService.getAllExpenses();

        Map<String, List<?>> report = new HashMap<>();
        report.put("invoices", invoices);
        report.put("expenses", expenses);

        return ResponseEntity.ok(report);
    }
}
