package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceReportsRepository extends JpaRepository<InvoiceReports,Long> {

    @Query("SELECT i FROM InvoiceReports i")
    List<InvoiceReports> getAllInvoices();
}