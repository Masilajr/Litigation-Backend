package com.LDLS.Litigation.Project.Reports;

import com.LDLS.Litigation.Project.UserRegistration.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReposrtsRepository extends JpaRepository<UserRegistration, Long> {
    @Query(value = "select * from user_registration",nativeQuery = true)
    List<Object[]> getAllUsers();
//    @Query(value = "SELECT id, first_name, last_name, loan_amount, amount_remaining, client_code, phone_no1, status FROM client WHERE status = $P{status}", nativeQuery = true)
//    List<Object[]>
}
