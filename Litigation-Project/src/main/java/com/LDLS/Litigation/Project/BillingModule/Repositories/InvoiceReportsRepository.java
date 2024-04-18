package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceReportsRepository extends JpaRepository<InvoiceReports,Long> {

}