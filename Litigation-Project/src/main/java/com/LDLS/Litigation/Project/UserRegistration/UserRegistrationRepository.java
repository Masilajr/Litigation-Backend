package com.LDLS.Litigation.Project.UserRegistration;

import com.LDLS.Litigation.Project.UserRegistration.DTO.UserRegistrationDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {

    // Method to find a UserRegistration by nationalIdNumber
    Optional<UserRegistration> findByNationalIdNumber(String nationalIdNumber);

    Optional<UserRegistration> findByEmail(String email);

    // Correctly using @EntityGraph annotation on the method
    @EntityGraph(attributePaths = {"privileges"})
    Optional<UserRegistration> findById(Long id);

    @Query("SELECT new com.LDLS.Litigation.Project.UserRegistration.DTO.UserRegistrationDTO(e.id, e.firstName, e.middleName, e.lastName, e.userId, e.email, e.phoneNumber, e.branch, e.nationalIdNumber, e.role, e.gender, e.username, e.firstLogin, e.accessPeriod, e.country, e.status) FROM UserRegistration e WHERE (:userId IS NULL OR e.userId = :userId) AND (:nationalIdNumber IS NULL OR e.nationalIdNumber = :nationalIdNumber)")
    List<UserRegistrationDTO> findByUserIdOrNationalIdNumber(String userId, String nationalIdNumber);

    long countByStatus(String status);


}
