package com.LDLS.Litigation.Project.Litigation.repositories;
import com.LDLS.Litigation.Project.Litigation.models.LitigationCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LitigationCaseRepository extends JpaRepository<LitigationCase, Long> {
    LitigationCase findByCaseReferenceNumber(String caseReferenceNumber);
}
