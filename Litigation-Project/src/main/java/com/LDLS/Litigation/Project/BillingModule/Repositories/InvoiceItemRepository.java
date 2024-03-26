package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.InvoiceItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends CrudRepository <InvoiceItem, Long> {

    InvoiceItem save(InvoiceItem item);
}
