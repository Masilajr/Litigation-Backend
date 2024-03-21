package com.LDLS.Litigation.Project.ClientManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientMangementRepository extends JpaRepository<ClientManagement, Long> {
    List<ClientManagement> findByStatus(String status);
    long countByStatus(String status);
}
