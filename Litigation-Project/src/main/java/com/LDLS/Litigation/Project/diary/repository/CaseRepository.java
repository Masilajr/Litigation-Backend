package com.LDLS.Litigation.Project.diary.repository;

import com.LDLS.Litigation.Project.diary.model.Cases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Cases, String> {
}

