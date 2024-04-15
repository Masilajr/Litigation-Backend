//package com.LDLS.Litigation.Project.BillingModule.Services;
//
//import com.LDLS.Litigation.Project.BillingModule.Entities.LoanCase;
//import com.LDLS.Litigation.Project.BillingModule.Repositories.LoanCaseRepository;
//import org.apache.xmlbeans.impl.xb.xsdschema.Public;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityNotFoundException;
//
//@Service
//public class LoanCaseService {
//
//    @Autowired
//    private static LoanCaseRepository loanCaseRepository;
//    private static Long loancaseId;
//
//    public LoanCase getLoanCaseDetailsByCaseId(Long loancaseId) {
//        LoanCase loanCaseDetails = loanCaseRepository.findById(loancaseId).orElse(null);
//        return loanCaseDetails;
//    }
//
//    public static LoanCase getLoanCaseDetailsByLoanCaseId(Long loanCaseId) {
//        return loanCaseRepository.findById(loancaseId)
//                .orElseThrow(() -> new EntityNotFoundException("LoanCase not found with id: " + loanCaseId));
//    }
//
//    // Removed unused constructor with Long parameter
//}
