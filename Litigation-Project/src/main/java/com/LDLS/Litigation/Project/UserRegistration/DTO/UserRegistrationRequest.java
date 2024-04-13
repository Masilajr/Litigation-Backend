package com.LDLS.Litigation.Project.UserRegistration.DTO;
import com.LDLS.Litigation.Project.UserRegistration.Privilege;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistration;
import lombok.Data;
import java.util.List;

@Data
public class UserRegistrationRequest {
    private UserRegistration userRegistration;
    private List<String> selectedPrivileges;
    private List<Privilege> privileges;

}