package com.LDLS.Litigation.Project.UserRegistration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class UserRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sn;
    @NonNull
    @Column(name = "firstname",  length = 50)
    private String firstName;
    private String middleName;
    @NonNull
    @Column(name = "lastname", length = 50)
    private String lastName;
    private String email;
    private Long phoneNumber;
    private String branch;
    @Column(name = "nationalIdNumber", length = 50)
    private String nationalIdNumber;
    private String role;
    private String gender;
    private String country;
    private Date accessPeriod;

}
