package com.LDLS.Litigation.Project.Litigation.repositories;

import com.LDLS.Litigation.Project.Litigation.models.DemandLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandLetterRepository extends JpaRepository<DemandLetter, Long> {

}

