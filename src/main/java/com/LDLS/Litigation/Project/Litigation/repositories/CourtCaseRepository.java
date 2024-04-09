package com.LDLS.Litigation.Project.Litigation.repositories;

import com.LDLS.Litigation.Project.Litigation.models.CourtCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtCaseRepository extends JpaRepository<CourtCase, Long> {

}
