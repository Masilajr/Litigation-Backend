package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Case;
import com.LDLS.Litigation.Project.BillingModule.Services.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/billing/cases")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/{caseId}")
    public ResponseEntity<Case> getCaseDetails(@PathVariable Long caseId) {
        Case caseDetails = caseService.getCaseDetailsByCaseId(caseId);
        if (caseDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(caseDetails);
    }
}