package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Entities.Case;
import org.springframework.stereotype.Service;

@Service
public interface CaseService {

    Case getCaseDetailsByCaseId(Long caseId);
}

