package com.LDLS.Litigation.Project.UserRegistration;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.UserRegistration.DTO.UserRegistrationRequest;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistrationService;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistrationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/userRegistration")
@CrossOrigin

public class UserRegistrationController {
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    PrivilegeRepository privilegeRepository;

//    @PostMapping("/create")
//    public ResponseEntity<EntityResponse> createUserRegistration(@RequestBody UserRegistration userRegistration) {
//        try {
//            EntityResponse response = userRegistrationService.createUserRegistration(userRegistration);
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }


    @PostMapping("/create")
    public ResponseEntity<EntityResponse> createUserRegistration(@RequestBody UserRegistrationRequest request) {
        try {
            // Extract user registration and selected privileges from the request
            UserRegistration userRegistration = request.getUserRegistration();
            Set<Privilege> privilegesSet = mapSelectedPrivileges(userRegistration.getPrivileges());

            // Set the extracted privileges to the user registration
            userRegistration.setPrivileges(privilegesSet);

            // Call service method with user registration
            EntityResponse response = userRegistrationService.createUserRegistration(userRegistration, privilegesSet); // Pass Set
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Method to map privilege names to Privilege objects
    private Set<Privilege> mapSelectedPrivileges(Set<Privilege> selectedPrivileges) {
        Set<Privilege> privileges = new HashSet<>();
        for (Privilege privilege : selectedPrivileges) {
            privileges.add(privilege);
        }
        return privileges;
    }





    @GetMapping("/get/{id}")
    public ResponseEntity<UserRegistration> getuserRegistrationById(@PathVariable Long id) {
        Optional<UserRegistration> userRegistrationOptional = userRegistrationService.getUserRegistrationById(id);
        if (userRegistrationOptional.isPresent()) {
            UserRegistration userRegistration = userRegistrationOptional.get();
            // Fetch user privileges
            userRegistration.getPrivileges().size(); // This triggers the lazy loading of privileges
            return ResponseEntity.ok(userRegistration);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/fetch")
    public List<UserRegistration> getAllUserRegistration() {
        return userRegistrationService.getAllCustomerRegistration();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserRegistration> updateUserRegistration(
            @PathVariable Long id, @RequestBody UserRegistration userRegistration)
    {
        UserRegistration updatedUserRegistration =
                userRegistrationService.updateUserRegistration(id, userRegistration);
        return ResponseEntity.ok(updatedUserRegistration);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EntityResponse> deleteUserRegistration(@PathVariable Long id) {
        EntityResponse response = userRegistrationService.deleteUserRegistrationById(id);
        return ResponseEntity.ok(response);
    }

}
