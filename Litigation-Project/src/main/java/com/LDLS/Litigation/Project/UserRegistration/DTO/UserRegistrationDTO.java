package com.LDLS.Litigation.Project.UserRegistration.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String userId;
    private String email;
    private String privilege;
    private Long phoneNumber;
    private String branch;
    private String nationalIdNumber;
    private String role;
    private String gender;
    private String username;
    private boolean firstLogin = true;
    private String temporaryPassword;
    private String accessPeriod;
    private String country;
    private String status;


    public UserRegistrationDTO(Long id, String firstName, String middleName, String lastName, String userId, String email, Long phoneNumber, String branch, String nationalIdNumber, String role, String gender, String username, boolean firstLogin, String accessPeriod, String country, String status) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.branch = branch;
        this.nationalIdNumber = nationalIdNumber;
        this.role = role;
        this.gender = gender;
        this.username = username;
        this.firstLogin = firstLogin;
        this.accessPeriod = accessPeriod;
        this.country = country;
        this.status = status;
    }



}
