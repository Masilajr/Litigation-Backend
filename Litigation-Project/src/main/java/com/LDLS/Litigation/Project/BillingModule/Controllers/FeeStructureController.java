package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Services.FeeStructureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/fee-structures")
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    public FeeStructureController(FeeStructureService feeStructureService) {
        this.feeStructureService = feeStructureService;
    }

    // ... CRUD methods for fee structures (similar to ExpenseTrackingController)

    // Additional method for calculating total rates
    @GetMapping("/{id}/calculate-total")
    public ResponseEntity<BigDecimal> calculateTotalBill(@PathVariable Long id, @RequestParam Integer hours) throws Throwable {
        BigDecimal totalBill = feeStructureService.calculateTotalBill(id, hours);
        return ResponseEntity.ok(totalBill);
    }
}
