package com.LDLS.Litigation.Project.diary.service;

import com.LDLS.Litigation.Project.diary.exception.ResourceNotFoundException;
import com.LDLS.Litigation.Project.diary.model.Cases;
import com.LDLS.Litigation.Project.diary.repository.CaseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseService {
    @Autowired
    private CaseRepository caseRepository;

    public CaseService() {
    }

    public Cases createCase(Cases caseDetails) {
        return (Cases)this.caseRepository.save(caseDetails);
    }

    public List<Cases> getAllCases() {
        return this.caseRepository.findAll();
    }

    public Cases getCaseById(String caseNumber) {
        return (Cases)this.caseRepository.findById(caseNumber).orElseThrow(() -> {
            return new ResourceNotFoundException("Case not found");
        });
    }

    public Cases updateCase(String caseNumber, Cases caseDetails) {
        Cases cases = this.getCaseById(caseNumber);
        cases.setDocuments(caseDetails.getDocuments());
        cases.setEvents(caseDetails.getEvents());
        return (Cases)this.caseRepository.save(cases);
    }

    public void deleteCase(String caseNumber) {
        Cases cases = this.getCaseById(caseNumber);
        this.caseRepository.delete(cases);
    }
}
