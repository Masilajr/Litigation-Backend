package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.LoanCase;
import com.LDLS.Litigation.Project.BillingModule.Repositories.LoanCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanCaseServiceImpl extends LoanCaseService {

    @Autowired
    private LoanCaseRepository loanCaseRepository; // Use consistent naming and remove static

    // Remove static and make method non-static
    public LoanCase getLoanCaseDetailsByCaseId(Long loanCaseId) {
        return loanCaseRepository.findById(loanCaseId).orElse(null);
    }

    // Remove unnecessary constructor with Long parameter
    // LoanCaseServiceImpl(Long loancaseId) {
    //     super(loancaseId);
    // }
}

