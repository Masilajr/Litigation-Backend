package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.ExpenseTracking;
import com.LDLS.Litigation.Project.BillingModule.Services.ExpenseTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseTrackingController {

    private final ExpenseTrackingService expenseTrackingService;

    public ExpenseTrackingController(ExpenseTrackingService expenseTrackingService) {
        this.expenseTrackingService = expenseTrackingService;
    }

    @PostMapping
    public ResponseEntity<ExpenseTracking> createExpense(@RequestBody ExpenseTracking expenseTracking) {
        ExpenseTracking createdExpense = expenseTrackingService.createExpense(expenseTracking);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseTracking> getExpenseById(@PathVariable Long id) {
        ExpenseTracking expenseTracking = expenseTrackingService.getExpenseById(id);
        if (expenseTracking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(expenseTracking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseTracking> updateExpense(@PathVariable Long id, @RequestBody ExpenseTracking expenseTracking) {
        expenseTracking.setId(id); // Ensure ID is set for update
        ExpenseTracking updatedExpense = expenseTrackingService.updateExpense(expenseTracking);
        if (updatedExpense == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseTrackingService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}