package com.LDLS.Litigation.Project.UserRegistration.DTO;

import com.LDLS.Litigation.Project.UserRegistration.Privilege;
import lombok.Data;

import java.util.ArrayList;
import java.util.Set;

@Data
public class UserRegistration {
    private Long id;
    private String firstName;
    private String lastName;
    private String privilege;
    // Other fields as needed

    public UserRegistration(Long id, String firstName, String lastName, String privilege) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.privilege = privilege;
    }



}
