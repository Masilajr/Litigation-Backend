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

    @GetMapping("/active-users")
    public long getActiveUsersCount() {
        return userRegistrationService.countActiveUsers();
    }

    @GetMapping("/locked-users")
    public long getLockedUsersCount() {
        return userRegistrationService.countLockedUsers();
    }


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
            @PathVariable Long id,
            @RequestBody UserRegistration userRegistration) {
        UserRegistration updatedUserRegistration = userRegistrationService.updateUserRegistration(id, userRegistration);
        return ResponseEntity.ok(updatedUserRegistration);
    }

//    @PutMapping("/status/{id}")
//    public ResponseEntity<UserRegistration> updateUserStatus(@PathVariable Long id, @RequestBody UserRegistrationDTO request) {
//        String status = request.getStatus();
//        try {
//            UserRegistration updatedUser = userRegistrationService.updateUserStatus(id, status);
//            return ResponseEntity.ok(updatedUser);
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
@PutMapping("/status/{id}")
public ResponseEntity<UserRegistration> updateUserStatus(@PathVariable Long id, @RequestBody UserRegistrationDTO request) {
    String status = request.getStatus(); // Get status from the request
    try {
        UserRegistration updatedUser = userRegistrationService.updateUserStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    } catch (UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EntityResponse> deleteUserRegistration(@PathVariable Long id) {
        EntityResponse response = userRegistrationService.deleteUserRegistrationById(id);
        return ResponseEntity.ok(response);
    }

}
