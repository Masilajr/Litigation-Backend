package com.LDLS.Litigation.Project.UserRegistration;

import com.LDLS.Litigation.Project.UserRegistration.Privilege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User_Registration")
public class UserRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String firstName;
    private String middleName;
    @NonNull
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
    private String temporaryPassword;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Privilege> privileges;

    private String accessPeriod;
    private String country;

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public UserRegistration(Long id, String firstName, String lastName, String privilege) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.privilege = privilege;
    }



}
