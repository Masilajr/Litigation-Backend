package com.LDLS.Litigation.Project.UserRegistration;

import com.LDLS.Litigation.Project.Authentication.Users.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {

    // Method to find a UserRegistration by nationalIdNumber
    Optional<UserRegistration> findByNationalIdNumber(String nationalIdNumber);
    Optional<UserRegistration> findByEmail(String email);

    // Correctly using @EntityGraph annotation on the method
    @EntityGraph(attributePaths = {"privileges"})
    Optional<UserRegistration> findById(Long id);
}
