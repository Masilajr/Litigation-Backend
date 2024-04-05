package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.LoanCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanCaseRepository extends JpaRepository <LoanCase, Long>  {
    Optional<LoanCase> findById(Long loancaseId);
}
