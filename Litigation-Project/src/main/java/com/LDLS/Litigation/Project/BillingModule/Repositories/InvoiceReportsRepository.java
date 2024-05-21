package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface InvoiceReportsRepository extends JpaRepository<InvoiceReports,Long> {

    @Query("SELECT i FROM InvoiceReports i")
    List<InvoiceReports> getAllInvoices();


    @Query("SELECT i.id FROM InvoiceReports i WHERE i.paymentStatus = 'approved'")
    List<Long> getApprovedInvoices();

    @Query("SELECT i.id FROM InvoiceReports i WHERE i.paymentStatus='Rejected'" )
    List<Long> getRejectedInvoices();

    @Query("SELECT SUM(i.recoveredAmount) FROM InvoiceReports i")
    Double getRecoveredAmount();

}