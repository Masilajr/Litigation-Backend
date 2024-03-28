package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.Case;
import com.LDLS.Litigation.Project.BillingModule.Repositories.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseServiceImpl extends CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Override
    public Case getCaseDetailsByCaseId(Long caseId) {
        return caseRepository.findById(caseId).orElse(null);
    }

}

