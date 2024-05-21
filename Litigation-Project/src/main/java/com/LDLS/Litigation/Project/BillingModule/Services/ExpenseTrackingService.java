package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.ExpenseTracking;
import com.LDLS.Litigation.Project.BillingModule.Repositories.ExpenseTrackingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTrackingService {

    private final ExpenseTrackingRepository expenseTrackingRepository;

    public ExpenseTrackingService(ExpenseTrackingRepository expenseTrackingRepository) {
        this.expenseTrackingRepository = expenseTrackingRepository;
    }

    public ExpenseTracking createExpense(ExpenseTracking expenseTracking) {
        return (ExpenseTracking) expenseTrackingRepository.save(expenseTracking);
    }

    public ExpenseTracking getExpenseById(Long id) {
        return (ExpenseTracking) expenseTrackingRepository.findById(id).orElse(null);
    }

    public ExpenseTracking updateExpense(ExpenseTracking expenseTracking) {
        return (ExpenseTracking) expenseTrackingRepository.save(expenseTracking);
    }

    public void deleteExpense(Long id) {
        expenseTrackingRepository.deleteById(id);
    }

    public List<Double> getTotalExpenses() {
        return expenseTrackingRepository.getTotalExpenses();
}
}