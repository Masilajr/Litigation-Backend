package com.LDLS.Litigation.Project.UserRegistration;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.UserRegistration.DTO.UserRegistrationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class UserRegistrationService {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserRegistrationDTO> getAllCustomerRegistration() {
        List<UserRegistration> userRegistrations = userRegistrationRepository.findAll();
        List<UserRegistrationDTO> userRegistrationDTOs = new ArrayList<>();
        for (UserRegistration userRegistration : userRegistrations) {
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
            userRegistrationDTO.setId(userRegistration.getId());
            userRegistrationDTO.setFirstName(userRegistration.getFirstName());
            userRegistrationDTO.setMiddleName(userRegistration.getMiddleName());
            userRegistrationDTO.setLastName(userRegistration.getLastName());
            userRegistrationDTO.setUserId(userRegistration.getUserId());
            userRegistrationDTO.setEmail(userRegistration.getEmail());
            userRegistrationDTO.setPrivilege(userRegistration.getPrivilege());
            userRegistrationDTO.setPhoneNumber(userRegistration.getPhoneNumber());
            userRegistrationDTO.setBranch(userRegistration.getBranch());
            userRegistrationDTO.setNationalIdNumber(userRegistration.getNationalIdNumber());
            userRegistrationDTO.setRole(userRegistration.getRole());
            userRegistrationDTO.setGender(userRegistration.getGender());
            userRegistrationDTO.setUsername(userRegistration.getUsername());
            userRegistrationDTO.setTemporaryPassword(userRegistration.getTemporaryPassword());
            userRegistrationDTO.setAccessPeriod(userRegistration.getAccessPeriod());
            userRegistrationDTO.setCountry(userRegistration.getCountry());

            userRegistrationDTOs.add(userRegistrationDTO);
        }
        return userRegistrationDTOs;
    }

    public Optional<UserRegistration> getUserRegistrationById(Long id) {
        return userRegistrationRepository.findById(id);
    }

    public EntityResponse<UserRegistrationDTO> createUserRegistration(UserRegistration userRegistration, Set<Privilege> privileges) {
        EntityResponse<UserRegistrationDTO> response = new EntityResponse<>();
        try {
            String dayMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMM"));
            String randomDigits = String.format("%04d", new Random().nextInt(10000));
            String userId = "USER" + "/" + dayMonth + "/" + randomDigits;
            userRegistration.setUserId(userId);
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();

            Optional<UserRegistration> existingUser = userRegistrationRepository.findByNationalIdNumber(userRegistration.getNationalIdNumber());
            if (existingUser.isPresent()) {
                response.setMessage("Provided user National Id already exists!!");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            }

            String randomPassword = passwordEncoder.encode("randomPassword");
            userRegistration.setTemporaryPassword(randomPassword);

            // Set user privileges
            List<Privilege> privilegeList = new ArrayList<>(privileges);
            userRegistration.setPrivileges(privilegeList);

            // Save user registration
            UserRegistration registration = userRegistrationRepository.save(userRegistration);

            response.setMessage("Successfully registered Customer");
            response.setEntity(userRegistrationDTO);
            response.setStatusCode(HttpStatus.CREATED.value());
        } catch (Exception e) {
            log.error("Unable to register user", e);
            response.setMessage("Failed to register user");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }


    public UserRegistration updateUserRegistration(Long id, UserRegistration newUserRegistration) {
        Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(id);
        if (optionalUserRegistration.isPresent()) {
            UserRegistration existingUserRegistration = optionalUserRegistration.get();
            // Copy non-null properties from newUserRegistration to existingUserRegistration
            BeanUtils.copyProperties(newUserRegistration, existingUserRegistration, "id", "privileges");
            // Update privileges separately
            existingUserRegistration.setPrivileges(newUserRegistration.getPrivileges());
            return userRegistrationRepository.save(existingUserRegistration);
        } else {
            throw new UserNotFoundException("Customer registration not found with id: " + id);
        }
    }
    public EntityResponse deleteUserRegistrationById(Long id) {
        EntityResponse res = new EntityResponse<>();
        try {
            Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(id);
            if (optionalUserRegistration.isPresent()) {
                userRegistrationRepository.deleteById(id);
                res.setMessage("User deleted successfully");
                res.setStatusCode(HttpStatus.OK.value());
            } else {
                res.setMessage("User not found");
                res.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            log.error("Exception", e);
            res.setMessage("Error deleting user");
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return res;
    }

}