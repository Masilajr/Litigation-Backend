package com.LDLS.Litigation.Project.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);
}
