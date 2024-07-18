package com.LDLS.Litigation.Project.diary.Controller;

import com.LDLS.Litigation.Project.diary.model.Cases;
import com.LDLS.Litigation.Project.diary.service.CaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/cases/"})
public class CaseController {
    @Autowired
    private CaseService caseService;

    public CaseController() {
    }

    @PostMapping({"/add"})
    public Cases createCase(@RequestBody Cases caseDetails) {
        return this.caseService.createCase(caseDetails);
    }

    @GetMapping({"/get"})
    public List<Cases> getAllCases() {
        return this.caseService.getAllCases();
    }

    @GetMapping({"/caseNumber"})
    public Cases getCaseById(@PathVariable String caseNumber) {
        return this.caseService.getCaseById(caseNumber);
    }

    @PutMapping({"/caseNumber"})
    public Cases updateCase(@PathVariable String caseNumber, @RequestBody Cases caseDetails) {
        return this.caseService.updateCase(caseNumber, caseDetails);
    }

    @DeleteMapping({"/caseNumber"})
    public ResponseEntity<?> deleteCase(@PathVariable String caseNumber) {
        this.caseService.deleteCase(caseNumber);
        return ResponseEntity.ok().build();
    }
}
