package com.LDLS.Litigation.Project.BillingModule.Repositories;

import com.LDLS.Litigation.Project.BillingModule.Entities.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeStructureRepository extends JpaRepository <FeeStructure, Long> {
     Optional<FeeStructure> findById(Long feeStructureId);
     //ScopedValue findById(Long feeStructureId);
}
