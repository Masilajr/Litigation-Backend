package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.BillingReport;
import com.LDLS.Litigation.Project.BillingModule.Entities.ExpenseTracking;
import com.LDLS.Litigation.Project.BillingModule.Entities.Invoice;
import com.LDLS.Litigation.Project.BillingModule.Repositories.ExpenseTrackingRepository;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingReportService {
    private final InvoiceRepository invoiceRepository;
    private final ExpenseTrackingRepository expenseTrackingRepository;

    public BillingReportService(InvoiceRepository invoiceRepository, ExpenseTrackingRepository expenseTrackingRepository) {
        this.invoiceRepository = invoiceRepository;
        this.expenseTrackingRepository = expenseTrackingRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<ExpenseTracking> getAllExpenses() {
        return (List<ExpenseTracking>) expenseTrackingRepository.findAll();
    }

    public BillingReport generateBillingReport() {
        List<Invoice> invoices = getAllInvoices();
        List<ExpenseTracking> expenses = getAllExpenses();


        BillingReport report = new BillingReport();
        for (Invoice invoice : invoices) {
            invoice.setBillingReport(report);
        }

        for (ExpenseTracking expense : expenses) {
            expense.setBillingReport(report);
        }
        return report;
    }

}


