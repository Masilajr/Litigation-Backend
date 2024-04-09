package com.LDLS.Litigation.Project.Litigation.services;

import com.LDLS.Litigation.Project.Litigation.dtos.LitigationCaseDTO;

public interface LitigationCaseInitiator {
    void processClientData(LitigationCaseDTO litigationCaseDTO);

    void fetchAndProcessCaseData(String caseId);
}
