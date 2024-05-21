package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceReports;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceReportsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InvoiceReportsService {

    private final InvoiceReportsRepository invoiceReportsRepository;

    @Autowired
    public InvoiceReportsService(InvoiceReportsRepository invoiceReportsRepository) {
        this.invoiceReportsRepository = invoiceReportsRepository;
    }

    public InvoiceReports generateInvoiceReports(InvoiceReports invoiceReports) {
        return invoiceReportsRepository.save(invoiceReports);
    }

    public List<InvoiceReports> getAllInvoicesReports() {
        return invoiceReportsRepository.findAll();
    }

    public InvoiceReports updateInvoiceReports(InvoiceReports invoiceReports) {
        return invoiceReports;
    }

    public void deleteInvoiceReports(Long id) {
    }

public Double getRecoveredAmount() {
    return invoiceReportsRepository.getRecoveredAmount();
}
    public List<Long> getApprovedInvoices() {
        return invoiceReportsRepository.getApprovedInvoices();
    }
    public List<Long> getRejectedInvoices() {
        return invoiceReportsRepository.getRejectedInvoices();
    }
}