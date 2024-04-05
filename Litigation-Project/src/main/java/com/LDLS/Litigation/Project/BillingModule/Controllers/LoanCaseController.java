package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.LoanCase;
import com.LDLS.Litigation.Project.BillingModule.Services.LoanCaseService;
import com.LDLS.Litigation.Project.BillingModule.Services.LoanCaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/billing/cases")
public class LoanCaseController {

    private final LoanCaseService loanCaseService;
    private final LoanCaseServiceImpl loanCaseServiceImpl;

    public LoanCaseController(LoanCaseService loanCaseService, LoanCaseServiceImpl loanCaseServiceImpl) {
        this.loanCaseService = loanCaseService;
        this.loanCaseServiceImpl = loanCaseServiceImpl;
    }

    @GetMapping("/{loancaseId}")
    public ResponseEntity<LoanCase> getLoanCaseDetails(@PathVariable Long loancaseId) {
//        LoanCaseService loanCaseService = new LoanCaseService();
        LoanCase loanCaseDetails = LoanCaseService.getLoanCaseDetailsByLoanCaseId(loancaseId);
        if (loanCaseDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanCaseDetails);
    }
}