package com.LDLS.Litigation.Project.diary.repository;

import com.LDLS.Litigation.Project.diary.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}