package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.ExpenseTracking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseTrackingRepository extends CrudRepository<ExpenseTracking, Long> {
//    ScopedValue<ExpenseTracking> findById(Long id);

    ExpenseTracking save(ExpenseTracking expenseTracking);

    void deleteById(Long id);

    Optional<ExpenseTracking> findById(Long id);

    @Query("SELECT SUM(e.expenseAmount) FROM ExpenseTracking e")
    List<Double> getTotalExpenses();
}
