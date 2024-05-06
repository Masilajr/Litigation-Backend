package com.LDLS.Litigation.Project.UserRegistration;
import com.LDLS.Litigation.Project.Authentication.MailService.MailsService;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagement;
import com.LDLS.Litigation.Project.UserRegistration.DTO.UserRegistrationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityGraph;
import javax.sql.DataSource;
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
    @Autowired
    MailsService mailService;

    public long countActiveUsers() {
        return userRegistrationRepository.countByStatus("active");
    }

    public long countLockedUsers() {
        return userRegistrationRepository.countByStatus("locked");
    }

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
//            userRegistrationDTO.setPrivilege(userRegistration.getPrivilege());
            userRegistrationDTO.setPhoneNumber(userRegistration.getPhoneNumber());
            userRegistrationDTO.setBranch(userRegistration.getBranch());
            userRegistrationDTO.setNationalIdNumber(userRegistration.getNationalIdNumber());
            userRegistrationDTO.setRole(userRegistration.getRole());
            userRegistrationDTO.setGender(userRegistration.getGender());
            userRegistrationDTO.setUsername(userRegistration.getUsername());
            userRegistrationDTO.setTemporaryPassword(userRegistration.getTemporaryPassword());
            userRegistrationDTO.setAccessPeriod(userRegistration.getAccessPeriod());
            userRegistrationDTO.setCountry(userRegistration.getCountry());
            userRegistrationDTO.setStatus(userRegistration.getStatus());

            userRegistrationDTOs.add(userRegistrationDTO);
        }
        return userRegistrationDTOs;
    }

//


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

            String randomPassword = generateRandomPassword();
            userRegistration.setTemporaryPassword(randomPassword);

            // Set user privileges
            List<Privilege> privilegeList = new ArrayList<>(privileges);
            userRegistration.setPrivileges(privilegeList);
            // Inside createUserRegistration method
            userRegistration.setStatus("Active");


            // Save user registration
            UserRegistration registration = userRegistrationRepository.save(userRegistration);

            String firstNameFromRequest = userRegistration.getFirstName();
            userRegistrationDTO.setFirstName(firstNameFromRequest); // Set first name

            // Prepare email parameters
            String toEmail = userRegistration.getEmail();
            String ccEmail = ""; // Optional CC email address
            String subject = "Your Temporary Password and Username";
            String message = "Dear " + userRegistrationDTO.getFirstName() + ",<br><br>"
                    + "Please find your temporary password and username below:<br><br>"
                    + "<strong>Username:</strong> " + userRegistration.getUsername() + "<br>"
                    + "<strong>Password:</strong> " + randomPassword + "<br><br>"
                    + "Please remember to change your password after logging in.<br><br>";
            boolean hasAttachment = false;
            String attachmentName = "";
            javax.activation.DataSource dataSource = null; // Assuming no attachment is being sent

            // Send email with temporary password and username
            try {
                mailService.SendEmail(toEmail, ccEmail, message, subject, hasAttachment, attachmentName, dataSource);
            } catch (MessagingException e) {
                log.error("Failed to send email", e);
                // Optionally, handle the failure, e.g., by setting an error message in the response
            }

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



    // Method to generate a random alphanumeric password with length 8
    private String generateRandomPassword() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = (int)(alphanumeric.length() * Math.random());
            sb.append(alphanumeric.charAt(index));
        }
        return sb.toString();
    }

    public List<UserRegistrationDTO> searchByUserIdOrNationalIdNumber(String userId, String nationalIdNumber) {
        if (userId == null && nationalIdNumber == null) {
            throw new IllegalArgumentException("At least one of userId or NationalIdNumber must be provided.");
        }
        return userRegistrationRepository.findByUserIdOrNationalIdNumber(userId, nationalIdNumber);
    }


    public UserRegistration updateUserRegistration(Long id, UserRegistration newUserRegistration) {
        Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(id);
        if (optionalUserRegistration.isPresent()) {
            UserRegistration existingUserRegistration = optionalUserRegistration.get();

            // Copy non-null properties from newUserRegistration to existingUserRegistration
            BeanUtils.copyProperties(newUserRegistration, existingUserRegistration, "id", "privileges", "status");

            // Update privileges separately
            existingUserRegistration.setPrivileges(newUserRegistration.getPrivileges());

            // Save the updated user registration
            return userRegistrationRepository.save(existingUserRegistration);
        } else {
            throw new UserNotFoundException("Customer registration not found with id: " + id);
        }
    }
    public UserRegistration updateUserStatus(Long id, String status) {
        // Validate if the provided status is either "Active" or "Locked"
        if (!status.equals("Active") && !status.equals("Locked")) {
            throw new IllegalArgumentException("Invalid status provided: " + status);
        }

        Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(id);
        if (optionalUserRegistration.isPresent()) {
            UserRegistration existingUserRegistration = optionalUserRegistration.get();
            existingUserRegistration.setStatus(status); // Update status here
            return userRegistrationRepository.save(existingUserRegistration);
        } else {
            throw new UserNotFoundException("Customer registration not found with id: " + id);
        }
    }


//    public UserRegistration updateUserStatus(Long id, String status) {
//        // Validate if the provided status is either "Active" or "Locked"
//        if (!status.equals("Active") && !status.equals("Locked")) {
//            throw new IllegalArgumentException("Invalid status provided: " + status);
//        }
//
//        Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(id);
//        if (optionalUserRegistration.isPresent()) {
//            UserRegistration existingUserRegistration = optionalUserRegistration.get();
//            existingUserRegistration.setStatus("Locked");
//            return userRegistrationRepository.save(existingUserRegistration);
//        } else {
//            throw new UserNotFoundException("Customer registration not found with id: " + id);
//        }
//    }
//public UserRegistration updateUserStatus(Long id, String status) {
//    try {
//        // Validate if the provided status is either "Active" or "Locked"
//        if (!status.equals("Active") && !status.equals("Locked")) {
//            throw new IllegalArgumentException("Invalid status provided: " + status);
//        }
//
//        // Log the ID and status being processed
//        System.out.println("Updating status for user with ID: " + id + " to " + status);
//
//        Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(id);
//        if (optionalUserRegistration.isPresent()) {
//            UserRegistration existingUserRegistration = optionalUserRegistration.get();
//            existingUserRegistration.setStatus("Locked");
//            return userRegistrationRepository.save(existingUserRegistration);
//        } else {
//            // Log user not found
//            System.out.println("User not found with ID: " + id);
//            throw new UserNotFoundException("Customer registration not found with id: " + id);
//        }
//    } catch (IllegalArgumentException e) {
//        // Log and rethrow the IllegalArgumentException
//        System.out.println("IllegalArgumentException occurred: " + e.getMessage());
//        throw e;
//    } catch (UserNotFoundException e) {
//        // Log and rethrow the UserNotFoundException
//        System.out.println("UserNotFoundException occurred: " + e.getMessage());
//        throw e;
//    } catch (Exception e) {
//        // Log any other unexpected exceptions
//        System.out.println("Unexpected exception occurred: " + e.getMessage());
//        throw e;
//    }
//}
//



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

//    public Optional<UserRegistration> getUserRegistrationByIdWithPrivileges(Long id) {
//        return userRegistrationRepository.findById(id);
//    }

    public Optional<UserRegistrationDTO> getUserRegistrationDTOById(Long id) {
        return userRegistrationRepository.findById(id)
                .map(this::mapToDTO);
    }

    private UserRegistrationDTO mapToDTO(UserRegistration userRegistration) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setId(userRegistration.getId());
        dto.setFirstName(userRegistration.getFirstName());
        dto.setMiddleName(userRegistration.getMiddleName());
        dto.setLastName(userRegistration.getLastName());
        dto.setUserId(userRegistration.getUserId());
        dto.setEmail(userRegistration.getEmail());
        // Exclude privilege field
        //dto.setPrivilege(userRegistration.getPrivilege());
        dto.setPhoneNumber(userRegistration.getPhoneNumber());
        dto.setBranch(userRegistration.getBranch());
        dto.setNationalIdNumber(userRegistration.getNationalIdNumber());
        dto.setRole(userRegistration.getRole());
        dto.setGender(userRegistration.getGender());
        dto.setUsername(userRegistration.getUsername());
        dto.setTemporaryPassword(userRegistration.getTemporaryPassword());
        dto.setAccessPeriod(userRegistration.getAccessPeriod());
        dto.setCountry(userRegistration.getCountry());
        return dto;
    }



}