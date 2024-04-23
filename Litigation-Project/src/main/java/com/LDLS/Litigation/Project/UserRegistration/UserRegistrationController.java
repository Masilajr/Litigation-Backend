package com.LDLS.Litigation.Project.UserRegistration;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.UserRegistration.DTO.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;


import java.util.*;

@RestController
@RequestMapping("/api/v1/userRegistration")
@CrossOrigin

public class UserRegistrationController {
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    EntityManager entityManager;

//    @PostMapping("/create")
//    public ResponseEntity<EntityResponse> createUserRegistration(@RequestBody UserRegistrationDTO userRegistration) {
//        try {
//            EntityResponse response = userRegistrationService.createUserRegistration(userRegistration);
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<EntityResponse<UserRegistrationDTO>> createUserRegistration(@RequestBody UserRegistration userRegistration) {
        try {
            // Extract selected privileges from the request
            Set<Privilege> privileges = mapSelectedPrivileges(userRegistration.getPrivileges());

            // Call service method with user registration and privileges
            EntityResponse<UserRegistrationDTO> response = userRegistrationService.createUserRegistration(userRegistration, privileges);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private Set<Privilege> mapSelectedPrivileges(List<Privilege> privilegeDTOList) {
        Set<Privilege> privileges = new HashSet<>();
        for (Privilege privilegeDTO : privilegeDTOList) {
            // Assuming Privilege has a name field
            Privilege privilege = new Privilege();
            privilege.setName(privilegeDTO.getName());
            privileges.add(privilege);
        }
        return privileges;
    }



//    @GetMapping("/get/{id}")
//    public ResponseEntity<UserRegistration> getuserRegistrationById(@PathVariable Long id) {
//        Optional<UserRegistration> userRegistrationOptional = userRegistrationService.getUserRegistrationByIdWithPrivileges(id);
//
//        if (userRegistrationOptional.isPresent()) {
//            UserRegistration userRegistration = userRegistrationOptional.get();
//            return ResponseEntity.ok(userRegistration);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserRegistrationDTO> getUserRegistrationById(@PathVariable Long id) {
        return userRegistrationService.getUserRegistrationDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<UserRegistrationDTO> search(@RequestParam(required = false) String userId, @RequestParam(required = false) String nationalIdNumber) {
        return userRegistrationService.searchByUserIdOrNationalIdNumber(userId, nationalIdNumber);
    }


    @GetMapping("/fetch")
    public ResponseEntity<EntityResponse<List<UserRegistrationDTO>>> getAllUserRegistration() {
        try {

            List<UserRegistrationDTO> userRegistrations = userRegistrationService.getAllCustomerRegistration();

            EntityResponse<List<UserRegistrationDTO>> response = new EntityResponse<>();
            response.setMessage("Successfully fetched all user registrations");
            response.setEntity(userRegistrations);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
