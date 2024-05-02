package com.LDLS.Litigation.Project.Reports;

import com.LDLS.Litigation.Project.UserRegistration.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReposrtsRepository extends JpaRepository<UserRegistration, Long> {
    @Query(value = "select * from user_registration",nativeQuery = true)
    List<Object[]> getAllUsers();
}
