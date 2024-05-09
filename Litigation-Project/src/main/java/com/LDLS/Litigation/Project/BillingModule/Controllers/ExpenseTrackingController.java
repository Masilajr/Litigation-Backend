package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.BillingModule.Entities.ExpenseTracking;
import com.LDLS.Litigation.Project.BillingModule.Services.ExpenseTrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/expenses")
public class ExpenseTrackingController {

    private final ExpenseTrackingService expenseTrackingService;

    public ExpenseTrackingController(ExpenseTrackingService expenseTrackingService) {
        this.expenseTrackingService = expenseTrackingService;
    }

    @PostMapping("/expenses")
    public ResponseEntity<EntityResponse> createExpense(@Valid @RequestBody ExpenseTracking expenseTracking) {
        try {
            log.info("Attempting to create expense: {}", expenseTracking);
            ExpenseTracking createdExpense = expenseTrackingService.createExpense(expenseTracking);
            log.info("Expense created successfully: {}", createdExpense);

            EntityResponse response = new EntityResponse(
                    "Expense created successfully",
                    createdExpense,
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("An error occurred while creating the expense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EntityResponse("An error occurred while creating the expense: " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse> getExpenseById(@PathVariable Long id) {
        try {
            ExpenseTracking expenseTracking = expenseTrackingService.getExpenseById(id);
            if (expenseTracking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityResponse("Expense not found", null, HttpStatus.NOT_FOUND.value()));
            }
            return ResponseEntity.ok(new EntityResponse("Expense retrieved successfully", expenseTracking, HttpStatus.OK.value()));
        } catch (Exception e) {
            log.error("An error occurred while retrieving the expense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EntityResponse("An error occurred while retrieving the expense: " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseTracking expenseTracking) {
        try {
            expenseTracking.setId(id); // Ensure ID is set for update
            ExpenseTracking updatedExpense = expenseTrackingService.updateExpense(expenseTracking);
            if (updatedExpense == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityResponse("Expense not found", null, HttpStatus.NOT_FOUND.value()));
            }
            return ResponseEntity.ok(new EntityResponse("Expense updated successfully", updatedExpense, HttpStatus.OK.value()));
        } catch (Exception e) {
            log.error("An error occurred while updating the expense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EntityResponse("An error occurred while updating the expense: " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse> deleteExpense(@PathVariable Long id) {
        try {
            expenseTrackingService.deleteExpense(id);
            return ResponseEntity.ok(new EntityResponse("Expense deleted successfully", null, HttpStatus.OK.value()));
        } catch (Exception e) {
            log.error("An error occurred while deleting the expense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EntityResponse("An error occurred while deleting the expense: " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
