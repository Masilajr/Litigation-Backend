package com.LDLS.Litigation.Project.UserRegistration.DTO;
import com.LDLS.Litigation.Project.UserRegistration.Privilege;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistration;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserRegistrationRequest {
    private UserRegistration userRegistration;
    private List<Privilege> selectedPrivileges;
    private List<Privilege> privileges;

        public List<Privilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}




