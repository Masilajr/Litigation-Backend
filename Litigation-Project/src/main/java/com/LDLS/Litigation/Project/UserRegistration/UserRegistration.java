package com.LDLS.Litigation.Project.UserRegistration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.*;
import java.util.HashSet;
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
    private String accessPeriod;
    private String country;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_privileges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges = new HashSet<>();

}
