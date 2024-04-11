package com.LDLS.Litigation.Project.Litigation.services;

import com.LDLS.Litigation.Project.Litigation.dtos.LitigationCaseDTO;

import java.util.List;

public interface LitigationCaseInitiator {
    void processClientData(LitigationCaseDTO litigationCaseDTO);

    void fetchAndProcessCaseData(String caseId);

    List<LitigationCaseDTO> getAllLitigationCases();
}
