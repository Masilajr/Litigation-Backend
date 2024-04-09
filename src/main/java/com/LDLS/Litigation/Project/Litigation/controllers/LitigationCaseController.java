package com.LDLS.Litigation.Project.Litigation.controllers;
import com.LDLS.Litigation.Project.Litigation.services.LitigationCaseInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/litigation")
public class LitigationCaseController {

    private final LitigationCaseInitiator litigationCaseInitiator;

    @Autowired
    public LitigationCaseController(LitigationCaseInitiator litigationCaseInitiator) {
        this.litigationCaseInitiator = litigationCaseInitiator;
    }

    @GetMapping("/process/{caseId}")
    public void processCase(@PathVariable String caseId) {
        litigationCaseInitiator.fetchAndProcessCaseData(caseId);
    }
}
